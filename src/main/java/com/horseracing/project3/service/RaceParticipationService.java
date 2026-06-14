package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.RegisterHorseRequest;
import com.horseracing.project3.dto.request.RegisterJockeyRequest;
import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.HorseHealthStatus;
import com.horseracing.project3.enums.JockeyInvitationStatus;
import com.horseracing.project3.enums.RaceParticipationStatus;
import com.horseracing.project3.repository.HorseRepo;
import com.horseracing.project3.repository.JockeyRepo;
import com.horseracing.project3.repository.RaceParticipationRepo;
import com.horseracing.project3.repository.RaceScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RaceParticipationService {

    @Autowired
    private RaceParticipationRepo raceParticipationRepo;

    @Autowired
    private HorseRepo horseRepo;

    @Autowired
    private RaceScheduleRepo raceScheduleRepo;

    @Autowired
    private JockeyRepo jockeyRepo;

    public void saveRaceParticipation(RaceParticipation raceparticipation) {
        raceParticipationRepo.save(raceparticipation);
    }

    public RaceParticipation registerHorse(RegisterHorseRequest request, String ownerEmail) {
        Horse horse = horseRepo.findById(request.getHorseId())
                .orElseThrow(() -> new RuntimeException("Horse not found"));

        if (!horse.getHorseOwner().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("You do not own this horse");
        }

        if (horse.getHealthStatus() != HorseHealthStatus.ELIGIBLE) {
            throw new RuntimeException("Horse is not eligible to race (Health Status: " + horse.getHealthStatus() + ")");
        }

        if (horse.getAge() < 2) {
            throw new RuntimeException("Horse is underage. Minimum age is 2 years old.");
        }

        RaceSchedule schedule = raceScheduleRepo.findById(request.getRaceScheduleId())
                .orElseThrow(() -> new RuntimeException("RaceSchedule not found"));

        Tournament tournament = schedule.getTournament();
        if (tournament == null || Boolean.FALSE.equals(tournament.getRegistrationOpen())) {
            throw new RuntimeException("Registration for this tournament is closed");
        }

        LocalDateTime now = LocalDateTime.now();
        if (tournament.getRegistrationStartDate() != null && now.isBefore(tournament.getRegistrationStartDate())) {
            throw new RuntimeException("Registration has not started yet");
        }
        if (tournament.getRegistrationEndDate() != null && now.isAfter(tournament.getRegistrationEndDate())) {
            throw new RuntimeException("Registration has already ended");
        }

        if (raceParticipationRepo.existsByHorseIdAndRaceScheduleId(horse.getId(), schedule.getId())) {
            throw new RuntimeException("Horse is already registered for this race schedule");
        }

        RaceParticipation participation = new RaceParticipation();
        participation.setHorse(horse);
        participation.setRaceSchedule(schedule);
        participation.setStatus(RaceParticipationStatus.PENDING);
        
        return raceParticipationRepo.save(participation);
    }

    public RaceParticipation registerJockey(Integer participationId, RegisterJockeyRequest request, String ownerEmail) {
        RaceParticipation participation = raceParticipationRepo.findById(participationId)
                .orElseThrow(() -> new RuntimeException("RaceParticipation not found"));

        if (!participation.getHorse().getHorseOwner().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("You do not own the horse in this registration");
        }

        if (participation.getStatus() != RaceParticipationStatus.CONFIRMED) {
            throw new RuntimeException("Registration must be CONFIRMED before assigning a jockey");
        }

        Jockey jockey = jockeyRepo.findById(request.getJockeyId())
                .orElseThrow(() -> new RuntimeException("Jockey not found"));

        RaceSchedule currentSchedule = participation.getRaceSchedule();

        List<RaceParticipation> jockeySchedules = raceParticipationRepo.findByJockeyIdAndJockeyInvitationStatusNot(jockey.getId(), JockeyInvitationStatus.REJECTED);
        for (RaceParticipation jp : jockeySchedules) {
            RaceSchedule sched = jp.getRaceSchedule();
            if (sched.getId().equals(currentSchedule.getId()) && !jp.getId().equals(participation.getId())) {
                 throw new RuntimeException("Jockey is already participating in this race");
            }
            if (sched.getStartTime().isBefore(currentSchedule.getEndTime()) && sched.getEndTime().isAfter(currentSchedule.getStartTime())) {
                 throw new RuntimeException("Jockey has a scheduling conflict with another race");
            }
        }

        participation.setJockey(jockey);
        participation.setJockeyInvitationStatus(JockeyInvitationStatus.PENDING);

        return raceParticipationRepo.save(participation);
    }

    public RaceParticipation approveParticipation(Integer participationId) {
        RaceParticipation participation = raceParticipationRepo.findById(participationId)
                .orElseThrow(() -> new RuntimeException("RaceParticipation not found"));

        if (participation.getStatus() != RaceParticipationStatus.PENDING) {
            throw new com.horseracing.project3.exception.InvalidStatusTransitionException("Only PENDING participation can be APPROVED (CONFIRMED). Current status: " + participation.getStatus());
        }

        participation.setStatus(RaceParticipationStatus.CONFIRMED);
        return raceParticipationRepo.save(participation);
    }

    public RaceParticipation rejectParticipation(Integer participationId) {
        RaceParticipation participation = raceParticipationRepo.findById(participationId)
                .orElseThrow(() -> new RuntimeException("RaceParticipation not found"));

        if (participation.getStatus() != RaceParticipationStatus.PENDING) {
            throw new com.horseracing.project3.exception.InvalidStatusTransitionException("Only PENDING participation can be REJECTED. Current status: " + participation.getStatus());
        }

        participation.setStatus(RaceParticipationStatus.REJECTED);
        return raceParticipationRepo.save(participation);
    }
}
