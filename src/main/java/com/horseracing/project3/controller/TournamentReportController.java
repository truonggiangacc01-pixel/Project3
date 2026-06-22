package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.ExportRaceDataRequest;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}")
@CrossOrigin(origins = "*")
@Tag(name = "API Tournament Reports UC-31, UC-32, UC-34")
public class TournamentReportController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @GetMapping("/report")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> generateTournamentReport(@PathVariable Integer tournamentId) {
        try {
            return ResponseEntity.ok(raceUseCaseService.generateTournamentReport(tournamentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/export")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportRaceData(@PathVariable Integer tournamentId, @RequestBody ExportRaceDataRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.exportRaceData(tournamentId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportRaceDataGet(@PathVariable Integer tournamentId,
                                               @RequestParam(required = false, defaultValue = "all") String dataType,
                                               @RequestParam(required = false, defaultValue = "csv") String format) {
        try {
            return ResponseEntity.ok(raceUseCaseService.exportRaceData(tournamentId, new ExportRaceDataRequest(dataType, format)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> viewTournamentSchedule(@PathVariable Integer tournamentId) {
        return ResponseEntity.ok(raceUseCaseService.viewTournamentSchedule(tournamentId));
    }
}
