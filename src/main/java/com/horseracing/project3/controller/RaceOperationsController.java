package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.*;
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
            return ResponseEntity.ok(raceUseCaseService.delayRace(raceId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{raceId}/predictions/reopen")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> reopenPrediction(@PathVariable Integer raceId, @RequestParam(defaultValue = "true") boolean reopen) {
        try {
            return ResponseEntity.ok(raceUseCaseService.reopenPrediction(raceId, reopen));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{raceId}/start")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> startRace(@PathVariable Integer raceId, @RequestBody StartRaceRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.startRace(raceId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{raceId}/results")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> recordRaceResult(@PathVariable Integer raceId, @RequestBody RecordRaceResultRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.recordRaceResult(raceId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{raceId}/reports/pre-race")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> submitPreRaceReport(@PathVariable Integer raceId, @RequestBody RaceReportRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.submitPreRaceReport(raceId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{raceId}/reports/post-race")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> submitPostRaceReport(@PathVariable Integer raceId, @RequestBody RaceReportRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.submitPostRaceReport(raceId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{raceId}/violations")
    @PreAuthorize("hasAuthority('ROLE_RACE_REFEREE')")
    public ResponseEntity<?> handleRuleViolation(@PathVariable Integer raceId, @RequestBody RuleViolationRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.handleRuleViolation(raceId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{raceId}/results/publish")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> publishRaceResult(@PathVariable Integer raceId) {
        try {
            return ResponseEntity.ok(raceUseCaseService.publishRaceResult(raceId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
