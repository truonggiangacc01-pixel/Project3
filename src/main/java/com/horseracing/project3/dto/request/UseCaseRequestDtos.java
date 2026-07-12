package com.horseracing.project3.dto.request;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class UseCaseRequestDtos {

    public record DelayRaceRequest(
            String reason,
            LocalDateTime newStartTime,
            LocalDateTime newEndTime
    ) {
    }

    public record RaceTrackRequest(
            String name,
            String location,
            String surfaceType,
            Integer lengthMeters,
            String description
    ) {
    }

    public record StartRaceRequest(
            Boolean conditionsConfirmed,
            Boolean badWeather,
            String delayReason
    ) {
    }

    public record RaceResultItemRequest(
            Integer participationId,
            Integer rankPosition,
            LocalTime finishTime
    ) {
    }

    public record RecordRaceResultRequest(
            Integer refereeId,
            List<RaceResultItemRequest> results
    ) {
    }

    public record RaceReportRequest(
            Integer refereeId,
            String content,
            String violationNote,
            Boolean hasComplaint
    ) {
    }

    public record InspectionItemRequest(
            Integer participationId,
            Boolean horseReady,
            Boolean jockeyReady,
            String note
    ) {
    }

    public record InspectionRequest(
            Integer refereeId,
            List<InspectionItemRequest> items
    ) {
    }

    public record RuleViolationRequest(
            Integer participationId,
            Integer horseId,
            Integer jockeyId,
            String description,
            String penalty,
            String evidence
    ) {
    }

    public record ExportRaceDataRequest(
            String dataType,
            String format
    ) {
    }

    public record SendNotificationRequest(
            String message,
            String recipientType,
            Integer recipientId
    ) {
    }
}
