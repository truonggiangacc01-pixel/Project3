package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.ExportRaceDataRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.ExportedFile;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.RaceScheduleResponse;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
            return ResponseEntity.ok(new ApiResponse<>(true, "Tournament report generated", raceUseCaseService.generateTournamentReport(tournamentId)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/export")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportRaceData(@PathVariable Integer tournamentId, @RequestBody ExportRaceDataRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race data exported", raceUseCaseService.exportRaceData(tournamentId, request)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportRaceDataGet(@PathVariable Integer tournamentId,
                                               @RequestParam(required = false, defaultValue = "all") String dataType,
                                               @RequestParam(required = false, defaultValue = "csv") String format) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race data exported", raceUseCaseService.exportRaceData(tournamentId, new ExportRaceDataRequest(dataType, format))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/export/download")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> downloadRaceData(@PathVariable Integer tournamentId,
                                              @RequestParam(required = false, defaultValue = "all") String dataType,
                                              @RequestParam(required = false, defaultValue = "csv") String format) {
        try {
            ExportedFile file = raceUseCaseService.exportRaceDataFile(tournamentId, new ExportRaceDataRequest(dataType, format));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.fileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.contentType())
                    .body(file.content());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> viewTournamentSchedule(@PathVariable Integer tournamentId) {
        var schedules = raceUseCaseService.viewTournamentSchedule(tournamentId).stream().map(RaceScheduleResponse::from).toList();
        String message = schedules.isEmpty() ? "Tournament schedule is pending" : "Tournament schedule loaded";
        return ResponseEntity.ok(new ApiResponse<>(true, message, schedules));
    }
}
