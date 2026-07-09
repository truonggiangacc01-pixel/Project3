package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.*;
import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.*;
import com.horseracing.project3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RaceUseCaseService {

    @Autowired
    private RaceScheduleRepo raceScheduleRepo;

    @Autowired
    private RaceTrackRepo raceTrackRepo;

    @Autowired
    private PredictionRepo predictionRepo;

    @Autowired
    private RaceParticipationRepo raceParticipationRepo;

    @Autowired
    private RaceResultRepo raceResultRepo;

    @Autowired
    private RaceRefereeRepo raceRefereeRepo;

    @Autowired
    private RaceReportRepo raceReportRepo;

    @Autowired
    private RuleViolationRepo ruleViolationRepo;

    @Autowired
    private RankingEntryRepo rankingEntryRepo;

    @Autowired
    private TournamentRepo tournamentRepo;

    @Autowired
    private HorseRepo horseRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private HorseOwnerRepo horseOwnerRepo;

    @Autowired
    private JockeyRepo jockeyRepo;

    public RaceSchedule delayRace(Integer raceId, DelayRaceRequest request) {
        RaceSchedule race = findRace(raceId);
        if (race.getStatus() == RaceScheduleStatus.RUNNING || race.getStatus() == RaceScheduleStatus.ONGOING
                || race.getStatus() == RaceScheduleStatus.COMPLETED) {
            throw new RuntimeException("Cannot delay a race that has already started or completed");
        }
        if (isBlank(request.reason())) {
            throw new RuntimeException("Delay reason is required");
        }

        race.setDelayReason(request.reason());
        race.setStatus(RaceScheduleStatus.DELAYED);
        if (request.newStartTime() != null && request.newEndTime() != null) {
            validateRaceTime(request.newStartTime(), request.newEndTime());
            race.setStartTime(request.newStartTime());
            race.setEndTime(request.newEndTime());
            race.setRaceDate(request.newStartTime().toLocalDate());
        }
        return raceScheduleRepo.save(race);
    }

    public RaceTrack createRaceTrack(RaceTrackRequest request) {
        validateRaceTrack(request);
        if (raceTrackRepo.findByName(request.name()).isPresent()) {
            throw new RuntimeException("Tên trường đua đã tồn tại");
        }
        RaceTrack track = new RaceTrack();
        applyRaceTrack(track, request);
        return raceTrackRepo.save(track);
    }

    public RaceTrack updateRaceTrack(Integer trackId, RaceTrackRequest request) {
        validateRaceTrack(request);
        RaceTrack track = raceTrackRepo.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Race track not found"));
        applyRaceTrack(track, request);
        return raceTrackRepo.save(track);
    }

    public List<RaceTrack> getRaceTracks() {
        return raceTrackRepo.findAll();
    }

    public Map<String, Object> reopenPrediction(Integer raceId, boolean reopen) {
        RaceSchedule race = findRace(raceId);
        if (race.getStatus() != RaceScheduleStatus.DELAYED) {
            throw new RuntimeException("Only delayed races can reopen prediction");
        }
        if (!reopen) {
            return Map.of("raceId", raceId, "reopened", false, "message", "Prediction remains closed");
        }
        List<Prediction> predictions = predictionRepo.findByRaceScheduleId(raceId);
        predictions.forEach(prediction -> prediction.setStatus(PredictionStatus.OPEN));
        predictionRepo.saveAll(predictions);
        return Map.of("raceId", raceId, "reopened", true, "updatedPredictions", predictions.size());
    }

    public Map<String, Object> closeDuePredictions() {
        LocalDateTime now = LocalDateTime.now();
        List<RaceSchedule> races = raceScheduleRepo.findAll();
        int closed = 0;
        for (RaceSchedule race : races) {
            if (race.getStatus() == RaceScheduleStatus.DELAYED) {
                continue;
            }
            if (!race.getStartTime().isAfter(now.plusMinutes(15))) {
                List<Prediction> predictions = predictionRepo.findByRaceScheduleId(race.getId());
                for (Prediction prediction : predictions) {
                    if (prediction.getStatus() == PredictionStatus.OPEN || prediction.getStatus() == PredictionStatus.PENDING) {
                        prediction.setStatus(PredictionStatus.CLOSED);
                        closed++;
                    }
                }
                predictionRepo.saveAll(predictions);
            }
        }
        return Map.of("closedPredictions", closed);
    }

    public RaceSchedule startRace(Integer raceId, StartRaceRequest request) {
        RaceSchedule race = findRace(raceId);
        if (Boolean.TRUE.equals(request.badWeather())) {
            DelayRaceRequest delayRequest = new DelayRaceRequest(
                    isBlank(request.delayReason()) ? "Bad weather" : request.delayReason(),
                    null,
                    null
            );
            return delayRace(raceId, delayRequest);
        }
        if (!Boolean.TRUE.equals(request.conditionsConfirmed())) {
            throw new RuntimeException("Race conditions must be confirmed");
        }
        if (LocalDateTime.now().isBefore(race.getStartTime())) {
            throw new RuntimeException("Race start time has not arrived");
        }
        long approvedHorses = raceParticipationRepo.findByRaceScheduleId(raceId).stream()
                .filter(p -> p.getStatus() == RaceParticipationStatus.CONFIRMED)
                .count();
        if (approvedHorses < 2) {
            throw new RuntimeException("Less than 2 approved horses. Cannot start race.");
        }
        if (race.getRaceTrack() == null && isBlank(race.getLocation())) {
            throw new RuntimeException("Race track information is required");
        }
        race.setStatus(RaceScheduleStatus.RUNNING);
        return raceScheduleRepo.save(race);
    }

    public List<RaceResult> recordRaceResult(Integer raceId, RecordRaceResultRequest request) {
        RaceSchedule race = findRace(raceId);
        if (race.getStatus() != RaceScheduleStatus.COMPLETED) {
            throw new RuntimeException("Race must be completed before recording results");
        }
        if (request.results() == null || request.results().isEmpty()) {
            throw new RuntimeException("Race result data is required");
        }
        RaceReferee referee = null;
        if (request.refereeId() != null) {
            referee = raceRefereeRepo.findById(request.refereeId())
                    .orElseThrow(() -> new RuntimeException("Race referee not found"));
        }

        List<RaceResult> saved = new ArrayList<>();
        Set<Integer> rankPositions = new HashSet<>();
        for (RaceResultItemRequest item : request.results()) {
            if (item.participationId() == null || item.rankPosition() == null || item.finishTime() == null) {
                throw new RuntimeException("Participation, rank position, and finish time are required");
            }
            if (!rankPositions.add(item.rankPosition())) {
                throw new RuntimeException("Duplicate rank position: " + item.rankPosition());
            }
            RaceParticipation participation = raceParticipationRepo.findById(item.participationId())
                    .orElseThrow(() -> new RuntimeException("Race participation not found: " + item.participationId()));
            if (participation.getRaceSchedule() == null || !participation.getRaceSchedule().getId().equals(raceId)) {
                throw new RuntimeException("Participation does not belong to this race: " + item.participationId());
            }

            RaceResult result = new RaceResult();
            result.setRaceSchedule(race);
            result.setRaceParticipation(participation);
            result.setRaceReferee(referee);
            result.setRankPosition(item.rankPosition());
            result.setFinishTime(item.finishTime());
            result.setStatus(RaceResultStatus.UNOFFICIAL);
            saved.add(raceResultRepo.save(result));
        }
        return saved;
    }

    public RaceReport submitPreRaceReport(Integer raceId, RaceReportRequest request) {
        RaceSchedule race = findRace(raceId);
        if (race.getStatus() == RaceScheduleStatus.RUNNING || race.getStatus() == RaceScheduleStatus.ONGOING
                || race.getStatus() == RaceScheduleStatus.COMPLETED) {
            throw new RuntimeException("Pre-race report must be submitted before the race starts");
        }
        long approvedHorses = raceParticipationRepo.findByRaceScheduleId(raceId).stream()
                .filter(p -> p.getStatus() == RaceParticipationStatus.CONFIRMED)
                .count();
        if (approvedHorses < 2) {
            throw new RuntimeException("Less than 2 approved horses. Cannot start race.");
        }
        return createReport(race, request, RaceReportType.PRE_RACE);
    }

    public RaceReport submitPostRaceReport(Integer raceId, RaceReportRequest request) {
        RaceSchedule race = findRace(raceId);
        if (race.getStatus() != RaceScheduleStatus.COMPLETED) {
            throw new RuntimeException("Post-race report must be submitted after race completion");
        }
        return createReport(race, request, RaceReportType.POST_RACE);
    }

    public RuleViolation handleRuleViolation(Integer raceId, RuleViolationRequest request) {
        RaceSchedule race = findRace(raceId);
        if (isBlank(request.description())) {
            throw new RuntimeException("Violation description is required");
        }
        RuleViolation violation = new RuleViolation();
        violation.setRaceSchedule(race);
        violation.setDescription(request.description());
        violation.setPenalty(request.penalty());
        violation.setEvidence(request.evidence());
        violation.setCreatedAt(LocalDateTime.now());
        violation.setStatus(isBlank(request.evidence()) ? RuleViolationStatus.PENDING_REVIEW : RuleViolationStatus.DECIDED);
        return ruleViolationRepo.save(violation);
    }

    public List<RaceResult> publishRaceResult(Integer raceId) {
        if (raceReportRepo.existsByRaceScheduleIdAndHasComplaintTrue(raceId)
                || ruleViolationRepo.existsByRaceScheduleIdAndStatus(raceId, RuleViolationStatus.PENDING_REVIEW)) {
            throw new RuntimeException("Cannot publish race result while complaint or pending violation review exists");
        }
        List<RaceResult> results = raceResultRepo.findByRaceScheduleId(raceId);
        if (results.isEmpty()) {
            throw new RuntimeException("No race results found");
        }
        results.forEach(result -> result.setStatus(RaceResultStatus.PUBLISHED));
        return raceResultRepo.saveAll(results);
    }

    public List<RankingEntry> updateRankingTable(Integer tournamentId) {
        Tournament tournament = tournamentRepo.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
        List<RaceSchedule> races = raceScheduleRepo.findByTournamentIdOrderByStartTimeAsc(tournamentId);
        Map<Integer, Integer> horsePoints = new HashMap<>();
        Map<Integer, Horse> horses = new HashMap<>();
        for (RaceSchedule race : races) {
            for (RaceResult result : raceResultRepo.findByRaceScheduleId(race.getId())) {
                if (result.getStatus() != RaceResultStatus.PUBLISHED && result.getStatus() != RaceResultStatus.OFFICIAL) {
                    continue;
                }
                Horse horse = result.getRaceParticipation().getHorse();
                if (horse == null || result.getRankPosition() == null) {
                    continue;
                }
                horses.put(horse.getId(), horse);
                horsePoints.merge(horse.getId(), calculatePoints(result.getRankPosition()), Integer::sum);
            }
        }
        if (horsePoints.isEmpty()) {
            throw new RuntimeException("No valid race result data for ranking");
        }

        List<Map.Entry<Integer, Integer>> ordered = horsePoints.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .toList();
        List<RankingEntry> saved = new ArrayList<>();
        int rank = 1;
        for (Map.Entry<Integer, Integer> entry : ordered) {
            RankingEntry rankingEntry = rankingEntryRepo.findByTournamentIdAndHorseId(tournamentId, entry.getKey())
                    .orElseGet(RankingEntry::new);
            rankingEntry.setTournament(tournament);
            rankingEntry.setHorse(horses.get(entry.getKey()));
            rankingEntry.setPoints(entry.getValue());
            rankingEntry.setRankPosition(rank++);
            rankingEntry.setUpdatedAt(LocalDateTime.now());
            saved.add(rankingEntryRepo.save(rankingEntry));
        }
        return saved;
    }

    public List<RankingEntry> viewRankingTable(Integer tournamentId) {
        return rankingEntryRepo.findByTournamentIdOrderByRankPositionAsc(tournamentId);
    }

    public Map<String, Object> generateTournamentReport(Integer tournamentId) {
        Tournament tournament = tournamentRepo.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
        List<RaceSchedule> races = raceScheduleRepo.findByTournamentIdOrderByStartTimeAsc(tournamentId);
        if (races.isEmpty()) {
            throw new RuntimeException("No tournament data found");
        }
        long completedRaces = races.stream().filter(r -> r.getStatus() == RaceScheduleStatus.COMPLETED).count();
        long resultCount = races.stream().mapToLong(r -> raceResultRepo.findByRaceScheduleId(r.getId()).size()).sum();
        return Map.of(
                "tournamentId", tournament.getId(),
                "tournamentName", tournament.getName(),
                "totalRaces", races.size(),
                "completedRaces", completedRaces,
                "results", resultCount,
                "reports", raceReportRepo.findByRaceScheduleTournamentId(tournamentId).size(),
                "rankingEntries", rankingEntryRepo.findByTournamentIdOrderByRankPositionAsc(tournamentId).size()
        );
    }

    public Map<String, Object> exportRaceData(Integer tournamentId, ExportRaceDataRequest request) {
        List<RaceSchedule> races = raceScheduleRepo.findByTournamentIdOrderByStartTimeAsc(tournamentId);
        if (races.isEmpty()) {
            throw new RuntimeException("No race data found");
        }
        String format = isBlank(request.format()) ? "csv" : request.format().toLowerCase();
        if (!format.equals("csv")) {
            throw new RuntimeException("Only csv export is supported");
        }
        StringBuilder csv = new StringBuilder("raceId,name,status,startTime,endTime,resultCount\n");
        for (RaceSchedule race : races) {
            csv.append(race.getId()).append(',')
                    .append(cleanCsv(race.getName())).append(',')
                    .append(race.getStatus()).append(',')
                    .append(race.getStartTime()).append(',')
                    .append(race.getEndTime()).append(',')
                    .append(raceResultRepo.findByRaceScheduleId(race.getId()).size())
                    .append('\n');
        }
        return Map.of("format", "csv", "fileName", "race-data-" + tournamentId + ".csv", "content", csv.toString());
    }

    public Notification sendNotification(SendNotificationRequest request) {
        if (isBlank(request.message())) {
            throw new RuntimeException("Notification message is required");
        }
        Notification notification = new Notification();
        notification.setMessage(request.message());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(NotificationStatus.UNREAD);
        String recipientType = isBlank(request.recipientType()) ? "" : request.recipientType().toUpperCase();
        if ("HORSE_OWNER".equals(recipientType)) {
            notification.setHorseOwner(horseOwnerRepo.findById(request.recipientId())
                    .orElseThrow(() -> new RuntimeException("Horse owner not found")));
        } else if ("JOCKEY".equals(recipientType)) {
            notification.setJockey(jockeyRepo.findById(request.recipientId())
                    .orElseThrow(() -> new RuntimeException("Jockey not found")));
        } else if ("RACE_REFEREE".equals(recipientType)) {
            notification.setRaceReferee(raceRefereeRepo.findById(request.recipientId())
                    .orElseThrow(() -> new RuntimeException("Race referee not found")));
        } else {
            throw new RuntimeException("recipientType must be HORSE_OWNER, JOCKEY, or RACE_REFEREE");
        }
        return notificationRepo.save(notification);
    }

    public List<RaceSchedule> viewTournamentSchedule(Integer tournamentId) {
        return raceScheduleRepo.findByTournamentIdOrderByStartTimeAsc(tournamentId);
    }

    public List<Horse> searchHorseInformation(String keyword) {
        if (isBlank(keyword)) {
            return horseRepo.findAll();
        }
        return horseRepo.findByNameContainingIgnoreCaseOrBreedContainingIgnoreCase(keyword, keyword);
    }

    private RaceSchedule findRace(Integer raceId) {
        return raceScheduleRepo.findById(raceId)
                .orElseThrow(() -> new RuntimeException("Race schedule not found"));
    }

    private void validateRaceTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new RuntimeException("Start time must be before end time");
        }
    }

    private void validateRaceTrack(RaceTrackRequest request) {
        if (isBlank(request.name()) || isBlank(request.location())) {
            throw new RuntimeException("Race track name and location are required");
        }
        if (request.lengthMeters() == null || request.lengthMeters() < 1000) {
            throw new RuntimeException("Chiều dài đường đua tối thiểu là 1000m");
        }
    }

    private void applyRaceTrack(RaceTrack track, RaceTrackRequest request) {
        track.setName(request.name());
        track.setLocation(request.location());
        track.setSurfaceType(request.surfaceType());
        track.setLengthMeters(request.lengthMeters());
        track.setDescription(request.description());
    }

    private RaceReport createReport(RaceSchedule race, RaceReportRequest request, RaceReportType reportType) {
        if (isBlank(request.content())) {
            throw new RuntimeException("Report content is required");
        }
        RaceReport report = new RaceReport();
        report.setRaceSchedule(race);
        report.setReportType(reportType);
        report.setContent(request.content());
        report.setViolationNote(request.violationNote());
        report.setHasComplaint(Boolean.TRUE.equals(request.hasComplaint()));
        report.setStatus(Boolean.TRUE.equals(request.hasComplaint()) ? RaceReportStatus.REVIEW : RaceReportStatus.SUBMITTED);
        report.setCreatedAt(LocalDateTime.now());
        if (request.refereeId() != null) {
            report.setRaceReferee(raceRefereeRepo.findById(request.refereeId())
                    .orElseThrow(() -> new RuntimeException("Race referee not found")));
        }
        return raceReportRepo.save(report);
    }

    private int calculatePoints(Integer rankPosition) {
        return switch (rankPosition) {
            case 1 -> 10;
            case 2 -> 7;
            case 3 -> 5;
            default -> Math.max(1, 5 - rankPosition);
        };
    }

    private String cleanCsv(String value) {
        return value == null ? "" : value.replace(",", " ");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
