package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.ExportRaceDataRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.ExportedFile;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
            return ResponseEntity.ok(new ApiResponse<>(true, "Race data exported", raceUseCaseService.exportRaceData(tournamentId, new ExportRaceDataRequest(dataType, format))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/export/download")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> downloadRaceData(@RequestParam Integer tournamentId,
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
}
