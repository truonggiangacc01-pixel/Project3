package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.*;
import com.horseracing.project3.dto.response.ExportedFile;
import com.horseracing.project3.entity.*;
import com.horseracing.project3.enums.*;
import com.horseracing.project3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
    private JockeyRankingEntryRepo jockeyRankingEntryRepo;

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

    @Autowired
    private PredictionService predictionService;

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
        List<Prediction> reopenedPredictions = predictions.stream()
                .filter(prediction -> prediction.getStatus() == PredictionStatus.CLOSED)
                .toList();
        reopenedPredictions.forEach(prediction -> prediction.setStatus(PredictionStatus.OPEN));
        predictionRepo.saveAll(reopenedPredictions);
        return Map.of("raceId", raceId, "reopened", true, "updatedPredictions", reopenedPredictions.size());
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
        if (race.getStatus() != RaceScheduleStatus.RUNNING && race.getStatus() != RaceScheduleStatus.ONGOING
                && race.getStatus() != RaceScheduleStatus.COMPLETED) {
            throw new RuntimeException("Race must be running or completed before recording results");
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
        if (race.getStatus() == RaceScheduleStatus.RUNNING || race.getStatus() == RaceScheduleStatus.ONGOING) {
            race.setStatus(RaceScheduleStatus.COMPLETED);
            raceScheduleRepo.save(race);
        }
        return saved;
    }

    public Map<String, Object> inspectRaceParticipants(Integer raceId, InspectionRequest request) {
        RaceSchedule race = findRace(raceId);
        if (race.getStatus() == RaceScheduleStatus.RUNNING || race.getStatus() == RaceScheduleStatus.ONGOING
                || race.getStatus() == RaceScheduleStatus.COMPLETED) {
            throw new RuntimeException("Inspection must be completed before the race starts");
        }
        if (request.items() == null || request.items().isEmpty()) {
            throw new RuntimeException("Inspection items are required");
        }
        if (request.refereeId() != null && !raceRefereeRepo.existsById(request.refereeId())) {
            throw new RuntimeException("Race referee not found");
        }

        int confirmed = 0;
        int scratched = 0;
        for (InspectionItemRequest item : request.items()) {
            RaceParticipation participation = raceParticipationRepo.findById(item.participationId())
                    .orElseThrow(() -> new RuntimeException("Race participation not found: " + item.participationId()));
            if (participation.getRaceSchedule() == null || !participation.getRaceSchedule().getId().equals(raceId)) {
                throw new RuntimeException("Participation does not belong to this race: " + item.participationId());
            }
            boolean ready = Boolean.TRUE.equals(item.horseReady()) && Boolean.TRUE.equals(item.jockeyReady());
            participation.setHorseReady(Boolean.TRUE.equals(item.horseReady()));
            participation.setJockeyReady(Boolean.TRUE.equals(item.jockeyReady()));
            participation.setInspectionNote(item.note());
            participation.setInspectedAt(LocalDateTime.now());
            participation.setStatus(ready ? RaceParticipationStatus.CONFIRMED : RaceParticipationStatus.SCRATCHED);
            raceParticipationRepo.save(participation);
            if (ready) {
                confirmed++;
            } else {
                scratched++;
            }
        }
        return Map.of("raceId", raceId, "confirmed", confirmed, "scratched", scratched);
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
        if (request.participationId() != null) {
            RaceParticipation participation = raceParticipationRepo.findById(request.participationId())
                    .orElseThrow(() -> new RuntimeException("Race participation not found"));
            if (participation.getRaceSchedule() == null || !participation.getRaceSchedule().getId().equals(raceId)) {
                throw new RuntimeException("Participation does not belong to this race");
            }
            violation.setRaceParticipation(participation);
            violation.setHorse(participation.getHorse());
            violation.setJockey(participation.getJockey());
        }
        if (request.horseId() != null) {
            violation.setHorse(horseRepo.findById(request.horseId())
                    .orElseThrow(() -> new RuntimeException("Horse not found")));
        }
        if (request.jockeyId() != null) {
            violation.setJockey(jockeyRepo.findById(request.jockeyId())
                    .orElseThrow(() -> new RuntimeException("Jockey not found")));
        }
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
        List<RaceResult> published = raceResultRepo.saveAll(results);
        predictionService.settleRacePredictions(raceId);
        return published;
    }

    public Map<String, Object> settleRacePredictions(Integer raceId) {
        findRace(raceId);
        return Map.of("raceId", raceId, "settledPredictions", predictionService.settleRacePredictions(raceId));
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

    public List<JockeyRankingEntry> updateJockeyRankingTable(Integer tournamentId) {
        Tournament tournament = tournamentRepo.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
        List<RaceSchedule> races = raceScheduleRepo.findByTournamentIdOrderByStartTimeAsc(tournamentId);
        Map<Integer, Integer> jockeyPoints = new HashMap<>();
        Map<Integer, Jockey> jockeys = new HashMap<>();
        for (RaceSchedule race : races) {
            for (RaceResult result : raceResultRepo.findByRaceScheduleId(race.getId())) {
                if (result.getStatus() != RaceResultStatus.PUBLISHED && result.getStatus() != RaceResultStatus.OFFICIAL) {
                    continue;
                }
                RaceParticipation participation = result.getRaceParticipation();
                Jockey jockey = participation == null ? null : participation.getJockey();
                if (jockey == null || result.getRankPosition() == null) {
                    continue;
                }
                jockeys.put(jockey.getId(), jockey);
                jockeyPoints.merge(jockey.getId(), calculatePoints(result.getRankPosition()), Integer::sum);
            }
        }
        if (jockeyPoints.isEmpty()) {
            throw new RuntimeException("No valid race result data for jockey ranking");
        }

        List<Map.Entry<Integer, Integer>> ordered = jockeyPoints.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .toList();
        List<JockeyRankingEntry> saved = new ArrayList<>();
        int rank = 1;
        for (Map.Entry<Integer, Integer> entry : ordered) {
            JockeyRankingEntry rankingEntry = jockeyRankingEntryRepo.findByTournamentIdAndJockeyId(tournamentId, entry.getKey())
                    .orElseGet(JockeyRankingEntry::new);
            rankingEntry.setTournament(tournament);
            rankingEntry.setJockey(jockeys.get(entry.getKey()));
            rankingEntry.setPoints(entry.getValue());
            rankingEntry.setRankPosition(rank++);
            rankingEntry.setUpdatedAt(LocalDateTime.now());
            saved.add(jockeyRankingEntryRepo.save(rankingEntry));
        }
        return saved;
    }

    public List<JockeyRankingEntry> viewJockeyRankingTable(Integer tournamentId) {
        return jockeyRankingEntryRepo.findByTournamentIdOrderByRankPositionAsc(tournamentId);
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
        ExportedFile file = exportRaceDataFile(tournamentId, request);
        return Map.of("format", format, "fileName", file.fileName(), "contentType", file.contentType(), "content", new String(file.content(), StandardCharsets.UTF_8));
    }

    public ExportedFile exportRaceDataFile(Integer tournamentId, ExportRaceDataRequest request) {
        List<RaceSchedule> races = raceScheduleRepo.findByTournamentIdOrderByStartTimeAsc(tournamentId);
        if (races.isEmpty()) {
            throw new RuntimeException("No race data found");
        }
        String format = isBlank(request.format()) ? "csv" : request.format().toLowerCase();
        return switch (format) {
            case "csv" -> new ExportedFile("race-data-" + tournamentId + ".csv", "text/csv", buildRaceCsv(races).getBytes(StandardCharsets.UTF_8));
            case "xls", "excel" -> new ExportedFile("race-data-" + tournamentId + ".xls", "application/vnd.ms-excel", buildRaceExcelHtml(races).getBytes(StandardCharsets.UTF_8));
            case "pdf" -> new ExportedFile("race-data-" + tournamentId + ".pdf", "application/pdf", buildSimplePdf(buildRaceTextLines(races)));
            default -> throw new RuntimeException("Supported export formats: csv, xls, pdf");
        };
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

    private String buildRaceCsv(List<RaceSchedule> races) {
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
        return csv.toString();
    }

    private String buildRaceExcelHtml(List<RaceSchedule> races) {
        StringBuilder html = new StringBuilder("<html><body><table><tr><th>raceId</th><th>name</th><th>status</th><th>startTime</th><th>endTime</th><th>resultCount</th></tr>");
        for (RaceSchedule race : races) {
            html.append("<tr><td>").append(race.getId()).append("</td><td>")
                    .append(escapeHtml(race.getName())).append("</td><td>")
                    .append(race.getStatus()).append("</td><td>")
                    .append(race.getStartTime()).append("</td><td>")
                    .append(race.getEndTime()).append("</td><td>")
                    .append(raceResultRepo.findByRaceScheduleId(race.getId()).size())
                    .append("</td></tr>");
        }
        return html.append("</table></body></html>").toString();
    }

    private List<String> buildRaceTextLines(List<RaceSchedule> races) {
        List<String> lines = new ArrayList<>();
        lines.add("Race export");
        lines.add("raceId | name | status | startTime | endTime | resultCount");
        for (RaceSchedule race : races) {
            lines.add(race.getId() + " | " + race.getName() + " | " + race.getStatus() + " | "
                    + race.getStartTime() + " | " + race.getEndTime() + " | "
                    + raceResultRepo.findByRaceScheduleId(race.getId()).size());
        }
        return lines;
    }

    private byte[] buildSimplePdf(List<String> lines) {
        StringBuilder text = new StringBuilder("BT /F1 10 Tf 40 780 Td ");
        for (String line : lines) {
            text.append('(').append(escapePdf(line)).append(") Tj 0 -16 Td ");
        }
        text.append("ET");
        byte[] stream = text.toString().getBytes(StandardCharsets.UTF_8);
        String header = "%PDF-1.4\n";
        String obj1 = "1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n";
        String obj2 = "2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n";
        String obj3 = "3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >> endobj\n";
        String obj4 = "4 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n";
        String obj5Prefix = "5 0 obj << /Length " + stream.length + " >> stream\n";
        String obj5Suffix = "\nendstream endobj\n";
        String body = obj1 + obj2 + obj3 + obj4 + obj5Prefix + text + obj5Suffix;
        return (header + body + "trailer << /Root 1 0 R >>\n%%EOF").getBytes(StandardCharsets.UTF_8);
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private String escapePdf(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
