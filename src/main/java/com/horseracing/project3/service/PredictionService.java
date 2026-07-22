package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.PredictionRequest;
import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.entity.Prediction;
import com.horseracing.project3.entity.RaceResult;
import com.horseracing.project3.entity.RaceSchedule;
import com.horseracing.project3.entity.Spectator;
import com.horseracing.project3.enums.PredictionStatus;
import com.horseracing.project3.enums.RaceResultStatus;
import com.horseracing.project3.enums.RaceScheduleStatus;
import com.horseracing.project3.repository.PredictionRepo;
import com.horseracing.project3.repository.HorseRepo;
import com.horseracing.project3.repository.RaceResultRepo;
import com.horseracing.project3.repository.RaceParticipationRepo;
import com.horseracing.project3.repository.RaceScheduleRepo;
import com.horseracing.project3.repository.SpectatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepo predictionRepo;

    @Autowired
    private SpectatorRepo spectatorRepo;

    @Autowired
    private RaceScheduleRepo raceScheduleRepo;

    @Autowired
    private HorseRepo horseRepo;

    @Autowired
    private RaceResultRepo raceResultRepo;

    @Autowired
    private RaceParticipationRepo raceParticipationRepo;

    @Autowired
    private WalletService walletService;

    public void savePrediction(Prediction prediction) {
        predictionRepo.save(prediction);
    }

    @Transactional
    public Prediction createPrediction(PredictionRequest request) {
        if (request.getSpectatorId() == null || request.getRaceScheduleId() == null || request.getHorseId() == null) {
            throw new RuntimeException("spectatorId, raceScheduleId, and horseId are required");
        }
        if (request.getStakeAmount() == null || request.getStakeAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Stake amount must be greater than zero");
        }

        Spectator spectator = spectatorRepo.findById(request.getSpectatorId())
                .orElseThrow(() -> new RuntimeException("Spectator not found"));
        RaceSchedule race = raceScheduleRepo.findById(request.getRaceScheduleId())
                .orElseThrow(() -> new RuntimeException("Race schedule not found"));
        Horse horse = horseRepo.findById(request.getHorseId())
                .orElseThrow(() -> new RuntimeException("Horse not found"));

        if (race.getStatus() == RaceScheduleStatus.RUNNING || race.getStatus() == RaceScheduleStatus.ONGOING
                || race.getStatus() == RaceScheduleStatus.COMPLETED || race.getStatus() == RaceScheduleStatus.CANCELLED) {
            throw new RuntimeException("Prediction is closed for this race");
        }
        if (race.getStartTime() != null && !race.getStartTime().isAfter(LocalDateTime.now().plusMinutes(15))) {
            throw new RuntimeException("Prediction gate is already closed");
        }
        boolean horseInRace = raceParticipationRepo.findByRaceScheduleId(race.getId()).stream()
                .anyMatch(p -> p.getHorse() != null && p.getHorse().getId().equals(horse.getId()));
        if (!horseInRace) {
            throw new RuntimeException("Horse does not participate in this race");
        }
        if (predictionRepo.existsBySpectatorIdAndRaceScheduleIdAndStatusIn(
                spectator.getId(),
                race.getId(),
                List.of(PredictionStatus.OPEN, PredictionStatus.PENDING, PredictionStatus.CLOSED))) {
            throw new RuntimeException("Spectator already has an active prediction for this race");
        }

        walletService.debitForPrediction(spectator, request.getStakeAmount());

        Prediction prediction = new Prediction();
        prediction.setNumber(horse.getId());
        prediction.setCreatedAt(LocalDateTime.now());
        prediction.setStatus(PredictionStatus.OPEN);
        prediction.setStakeAmount(request.getStakeAmount());
        prediction.setSpectator(spectator);
        prediction.setRaceSchedule(race);
        prediction.setHorse(horse);
        return predictionRepo.save(prediction);
    }

    public List<Prediction> getPredictionHistory(Integer spectatorId) {
        if (!spectatorRepo.existsById(spectatorId)) {
            throw new RuntimeException("Spectator not found");
        }
        return predictionRepo.findBySpectatorIdOrderByCreatedAtDesc(spectatorId);
    }

    @Transactional
    public Prediction cancelPrediction(Integer predictionId, Integer spectatorId) {
        if (predictionId == null || spectatorId == null) {
            throw new RuntimeException("predictionId and spectatorId are required");
        }

        Prediction prediction = predictionRepo.findById(predictionId)
                .orElseThrow(() -> new RuntimeException("Prediction not found"));
        if (prediction.getSpectator() == null || !prediction.getSpectator().getId().equals(spectatorId)) {
            throw new RuntimeException("Prediction does not belong to this spectator");
        }
        if (prediction.getStatus() != PredictionStatus.OPEN && prediction.getStatus() != PredictionStatus.PENDING) {
            throw new RuntimeException("Only OPEN or PENDING predictions can be cancelled");
        }

        RaceSchedule race = prediction.getRaceSchedule();
        if (race == null) {
            throw new RuntimeException("Race schedule not found");
        }
        if (race.getStatus() == RaceScheduleStatus.RUNNING || race.getStatus() == RaceScheduleStatus.ONGOING
                || race.getStatus() == RaceScheduleStatus.COMPLETED || race.getStatus() == RaceScheduleStatus.CANCELLED) {
            throw new RuntimeException("Prediction cannot be cancelled after race starts or is cancelled");
        }
        if (race.getStartTime() != null && !race.getStartTime().isAfter(LocalDateTime.now().plusMinutes(15))) {
            throw new RuntimeException("Prediction cancellation gate is already closed");
        }

        prediction.setStatus(PredictionStatus.REFUNDED);
        prediction.setPayoutAmount(prediction.getStakeAmount());
        walletService.refundPredictionStake(prediction.getSpectator(), prediction.getStakeAmount());
        return predictionRepo.save(prediction);
    }

    @Transactional
    public int settleRacePredictions(Integer raceId) {
        List<RaceResult> publishedResults = raceResultRepo.findByRaceScheduleId(raceId).stream()
                .filter(r -> r.getStatus() == RaceResultStatus.PUBLISHED || r.getStatus() == RaceResultStatus.OFFICIAL)
                .toList();
        if (publishedResults.isEmpty()) {
            return 0;
        }
        RaceResult winner = publishedResults.stream()
                .filter(r -> r.getRankPosition() != null && r.getRankPosition() == 1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Winner result is missing"));
        Integer winningHorseId = winner.getRaceParticipation().getHorse().getId();

        List<Prediction> predictions = predictionRepo.findByRaceScheduleId(raceId).stream()
                .filter(prediction -> prediction.getStatus() == PredictionStatus.OPEN
                        || prediction.getStatus() == PredictionStatus.PENDING
                        || prediction.getStatus() == PredictionStatus.CLOSED)
                .toList();
        BigDecimal totalStake = predictions.stream()
                .map(Prediction::getStakeAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal winningStake = predictions.stream()
                .filter(p -> p.getHorse() != null && p.getHorse().getId().equals(winningHorseId))
                .map(Prediction::getStakeAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int settled = 0;
        for (Prediction prediction : predictions) {
            if (prediction.getStatus() == PredictionStatus.WON || prediction.getStatus() == PredictionStatus.LOST) {
                continue;
            }
            if (prediction.getHorse() != null && prediction.getHorse().getId().equals(winningHorseId)) {
                BigDecimal payout = winningStake.compareTo(BigDecimal.ZERO) == 0
                        ? prediction.getStakeAmount()
                        : prediction.getStakeAmount().multiply(totalStake).divide(winningStake, 0, RoundingMode.DOWN);
                prediction.setStatus(PredictionStatus.WON);
                prediction.setPayoutAmount(payout);
                walletService.creditPredictionPayout(prediction.getSpectator(), payout);
            } else {
                prediction.setStatus(PredictionStatus.LOST);
                prediction.setPayoutAmount(BigDecimal.ZERO);
            }
            predictionRepo.save(prediction);
            settled++;
        }
        return settled;
    }
}
