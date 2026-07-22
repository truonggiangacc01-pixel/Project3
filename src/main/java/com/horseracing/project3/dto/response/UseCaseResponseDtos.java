package com.horseracing.project3.dto.response;

import com.horseracing.project3.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UseCaseResponseDtos {

    public record RaceScheduleResponse(
            Integer id,
            String name,
            LocalDate raceDate,
            String location,
            String status,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String delayReason,
            Integer tournamentId,
            Integer raceTrackId,
            String raceTrackName,
            Integer refereeId,
            String refereeName
    ) {
        public static RaceScheduleResponse from(RaceSchedule race) {
            RaceTrack raceTrack = race.getRaceTrack();
            RaceReferee referee = race.getRaceReferee();
            return new RaceScheduleResponse(
                    race.getId(),
                    race.getName(),
                    race.getRaceDate(),
                    race.getLocation(),
                    race.getStatus() == null ? null : race.getStatus().name(),
                    race.getStartTime(),
                    race.getEndTime(),
                    race.getDelayReason(),
                    race.getTournament() == null ? null : race.getTournament().getId(),
                    raceTrack == null ? null : raceTrack.getId(),
                    raceTrack == null ? null : raceTrack.getName(),
                    referee == null ? null : referee.getId(),
                    referee == null ? null : referee.getFullName()
            );
        }
    }

    public record RaceTrackResponse(
            Integer id,
            String name,
            String location,
            String surfaceType,
            Integer lengthMeters,
            String description
    ) {
        public static RaceTrackResponse from(RaceTrack track) {
            return new RaceTrackResponse(
                    track.getId(),
                    track.getName(),
                    track.getLocation(),
                    track.getSurfaceType(),
                    track.getLengthMeters(),
                    track.getDescription()
            );
        }
    }

    public record RaceResultResponse(
            Integer id,
            Integer raceId,
            Integer participationId,
            Integer horseId,
            String horseName,
            Integer rankPosition,
            LocalTime finishTime,
            String status
    ) {
        public static RaceResultResponse from(RaceResult result) {
            RaceParticipation participation = result.getRaceParticipation();
            Horse horse = participation == null ? null : participation.getHorse();
            return new RaceResultResponse(
                    result.getId(),
                    result.getRaceSchedule() == null ? null : result.getRaceSchedule().getId(),
                    participation == null ? null : participation.getId(),
                    horse == null ? null : horse.getId(),
                    horse == null ? null : horse.getName(),
                    result.getRankPosition(),
                    result.getFinishTime(),
                    result.getStatus() == null ? null : result.getStatus().name()
            );
        }
    }

    public record RaceReportResponse(
            Integer id,
            Integer raceId,
            String reportType,
            String content,
            String violationNote,
            Boolean hasComplaint,
            String status,
            LocalDateTime createdAt
    ) {
        public static RaceReportResponse from(RaceReport report) {
            return new RaceReportResponse(
                    report.getId(),
                    report.getRaceSchedule() == null ? null : report.getRaceSchedule().getId(),
                    report.getReportType() == null ? null : report.getReportType().name(),
                    report.getContent(),
                    report.getViolationNote(),
                    report.getHasComplaint(),
                    report.getStatus() == null ? null : report.getStatus().name(),
                    report.getCreatedAt()
            );
        }
    }

    public record RuleViolationResponse(
            Integer id,
            Integer raceId,
            Integer participationId,
            Integer horseId,
            Integer jockeyId,
            String description,
            String penalty,
            String evidence,
            String status,
            LocalDateTime createdAt
    ) {
        public static RuleViolationResponse from(RuleViolation violation) {
            return new RuleViolationResponse(
                    violation.getId(),
                    violation.getRaceSchedule() == null ? null : violation.getRaceSchedule().getId(),
                    violation.getRaceParticipation() == null ? null : violation.getRaceParticipation().getId(),
                    violation.getHorse() == null ? null : violation.getHorse().getId(),
                    violation.getJockey() == null ? null : violation.getJockey().getId(),
                    violation.getDescription(),
                    violation.getPenalty(),
                    violation.getEvidence(),
                    violation.getStatus() == null ? null : violation.getStatus().name(),
                    violation.getCreatedAt()
            );
        }
    }

    public record RankingEntryResponse(
            Integer id,
            Integer tournamentId,
            Integer horseId,
            String horseName,
            Integer points,
            Integer rankPosition,
            LocalDateTime updatedAt
    ) {
        public static RankingEntryResponse from(RankingEntry entry) {
            Horse horse = entry.getHorse();
            return new RankingEntryResponse(
                    entry.getId(),
                    entry.getTournament() == null ? null : entry.getTournament().getId(),
                    horse == null ? null : horse.getId(),
                    horse == null ? null : horse.getName(),
                    entry.getPoints(),
                    entry.getRankPosition(),
                    entry.getUpdatedAt()
            );
        }
    }

    public record JockeyRankingEntryResponse(
            Integer id,
            Integer tournamentId,
            Integer jockeyId,
            String jockeyName,
            Integer points,
            Integer rankPosition,
            LocalDateTime updatedAt
    ) {
        public static JockeyRankingEntryResponse from(JockeyRankingEntry entry) {
            Jockey jockey = entry.getJockey();
            return new JockeyRankingEntryResponse(
                    entry.getId(),
                    entry.getTournament() == null ? null : entry.getTournament().getId(),
                    jockey == null ? null : jockey.getId(),
                    jockey == null ? null : jockey.getFullName(),
                    entry.getPoints(),
                    entry.getRankPosition(),
                    entry.getUpdatedAt()
            );
        }
    }

    public record NotificationResponse(
            Integer id,
            String message,
            LocalDateTime createdAt,
            String status
    ) {
        public static NotificationResponse from(Notification notification) {
            return new NotificationResponse(
                    notification.getId(),
                    notification.getMessage(),
                    notification.getCreatedAt(),
                    notification.getStatus() == null ? null : notification.getStatus().name()
            );
        }
    }

    public record HorseSearchResponse(
            Integer id,
            String name,
            Integer age,
            String breed,
            String healthStatus,
            Integer ownerId,
            String ownerName
    ) {
        public static HorseSearchResponse from(Horse horse) {
            HorseOwner owner = horse.getHorseOwner();
            return new HorseSearchResponse(
                    horse.getId(),
                    horse.getName(),
                    horse.getAge(),
                    horse.getBreed(),
                    horse.getHealthStatus() == null ? null : horse.getHealthStatus().name(),
                    owner == null ? null : owner.getId(),
                    owner == null ? null : owner.getFullName()
            );
        }
    }
}
