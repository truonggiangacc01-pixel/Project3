package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.JockeyProfileUpdateRequest;
import com.horseracing.project3.dto.request.LicenseUpdateRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.entity.Jockey;
import com.horseracing.project3.service.JockeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import com.horseracing.project3.service.RaceParticipationService;
import java.util.List;

@RestController
@RequestMapping("/api/jockeys")
@CrossOrigin(origins = "*")
@Tag(name = "API Jockey Kiểm thử thành công")
public class JockeyController {

    @Autowired
    private JockeyService jockeyService;

    @Autowired
    private RaceParticipationService raceParticipationService;

    @GetMapping
    public ResponseEntity<List<Jockey>> getAllJockeys() {
        return ResponseEntity.ok(jockeyService.getAllJockeys());
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<ApiResponse<Jockey>> updateProfile(
            @PathVariable("id") Integer id,
            @Valid @RequestBody JockeyProfileUpdateRequest request) {
        
        ApiResponse<Jockey> response = jockeyService.updateJockeyProfile(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/license")
    public ResponseEntity<ApiResponse<Jockey>> updateLicense(
            @PathVariable("id") Integer id,
            @Valid @RequestBody LicenseUpdateRequest request) {
        ApiResponse<Jockey> response = jockeyService.updateLicense(
                id,
                request.getLicenseNumber(),
                request.getLicenseExpiryDate());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invitations")
    public ResponseEntity<?> getMyInvitations(Authentication authentication) {
        try {
            String email = authentication.getName();
            return ResponseEntity.ok(raceParticipationService.getInvitationsForJockey(email));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/invitations/{participationId}/respond")
    public ResponseEntity<?> respondToInvitation(
            @PathVariable Integer participationId,
            @RequestParam boolean isAccepted,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            return ResponseEntity.ok(raceParticipationService.respondToInvitation(participationId, isAccepted, email));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
