package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.RaceTrackRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.RaceTrackResponse;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/race-tracks")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Track UC-20 Kiểm thử thành công")
public class RaceTrackController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @GetMapping
    public ResponseEntity<?> getRaceTracks() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Race tracks loaded", raceUseCaseService.getRaceTracks().stream().map(RaceTrackResponse::from).toList()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createRaceTrack(@RequestBody RaceTrackRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race track created", RaceTrackResponse.from(raceUseCaseService.createRaceTrack(request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{trackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRaceTrack(@PathVariable Integer trackId, @RequestBody RaceTrackRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race track updated", RaceTrackResponse.from(raceUseCaseService.updateRaceTrack(trackId, request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{trackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteRaceTrack(@PathVariable Integer trackId) {
        try {
            raceUseCaseService.deleteRaceTrack(trackId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Race track deleted", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
