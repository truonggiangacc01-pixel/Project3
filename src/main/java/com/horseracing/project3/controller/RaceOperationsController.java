package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.*;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.*;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/races")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Operations UC-18, UC-21 to UC-29")
public class RaceOperationsController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @PutMapping("/{raceId}/delay")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delayRace(@PathVariable Integer raceId, @RequestBody DelayRaceRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race delayed successfully", RaceScheduleResponse.from(raceUseCaseService.delayRace(raceId, request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{raceId}/predictions/reopen")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> reopenPrediction(@PathVariable Integer raceId, @RequestParam(defaultValue = "true") boolean reopen) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Prediction status updated", raceUseCaseService.reopenPrediction(raceId, reopen)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{raceId}/start")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> startRace(@PathVariable Integer raceId, @RequestBody StartRaceRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race status updated", RaceScheduleResponse.from(raceUseCaseService.startRace(raceId, request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{raceId}/results")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> recordRaceResult(@PathVariable Integer raceId, @RequestBody RecordRaceResultRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race result recorded", raceUseCaseService.recordRaceResult(raceId, request).stream().map(RaceResultResponse::from).toList()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{raceId}/reports/pre-race")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> submitPreRaceReport(@PathVariable Integer raceId, @RequestBody RaceReportRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Pre-race report submitted", RaceReportResponse.from(raceUseCaseService.submitPreRaceReport(raceId, request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{raceId}/inspection")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> inspectRaceParticipants(@PathVariable Integer raceId, @RequestBody InspectionRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race participants inspected", raceUseCaseService.inspectRaceParticipants(raceId, request)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{raceId}/reports/post-race")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> submitPostRaceReport(@PathVariable Integer raceId, @RequestBody RaceReportRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Post-race report submitted", RaceReportResponse.from(raceUseCaseService.submitPostRaceReport(raceId, request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{raceId}/violations")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> handleRuleViolation(@PathVariable Integer raceId, @RequestBody RuleViolationRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Rule violation saved", RuleViolationResponse.from(raceUseCaseService.handleRuleViolation(raceId, request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{raceId}/results/publish")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> publishRaceResult(@PathVariable Integer raceId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race result published", raceUseCaseService.publishRaceResult(raceId).stream().map(RaceResultResponse::from).toList()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
