package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.RaceTrackRequest;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/race-tracks")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Track UC-20")
public class RaceTrackController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @GetMapping
    public ResponseEntity<?> getRaceTracks() {
        return ResponseEntity.ok(raceUseCaseService.getRaceTracks());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createRaceTrack(@RequestBody RaceTrackRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.createRaceTrack(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{trackId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRaceTrack(@PathVariable Integer trackId, @RequestBody RaceTrackRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.updateRaceTrack(trackId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
