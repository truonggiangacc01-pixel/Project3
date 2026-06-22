package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.SendNotificationRequest;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "*")
@Tag(name = "API System UC-22, UC-33")
public class SystemUseCaseController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @PostMapping("/predictions/close-due")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> closeDuePredictions() {
        try {
            return ResponseEntity.ok(raceUseCaseService.closeDuePredictions());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/notifications")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> sendNotification(@RequestBody SendNotificationRequest request) {
        try {
            return ResponseEntity.ok(raceUseCaseService.sendNotification(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/rankings/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRankingTable(@RequestParam Integer tournamentId) {
        try {
            return ResponseEntity.ok(raceUseCaseService.updateRankingTable(tournamentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
