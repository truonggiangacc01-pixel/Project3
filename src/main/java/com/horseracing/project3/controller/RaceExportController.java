package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.ExportRaceDataRequest;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/races")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Export UC-32")
public class RaceExportController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportRaceData(@RequestParam Integer tournamentId,
                                            @RequestParam(required = false, defaultValue = "all") String dataType,
                                            @RequestParam(required = false, defaultValue = "csv") String format) {
        try {
            return ResponseEntity.ok(raceUseCaseService.exportRaceData(tournamentId, new ExportRaceDataRequest(dataType, format)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
