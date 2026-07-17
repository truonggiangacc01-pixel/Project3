package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.RegisterHorseRequest;
import com.horseracing.project3.dto.request.RegisterJockeyRequest;
import com.horseracing.project3.entity.RaceParticipation;
import com.horseracing.project3.service.RaceParticipationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner/race-participations")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Participation for Owner")
public class RaceParticipationController {

    @Autowired
    private RaceParticipationService raceParticipationService;

    @GetMapping
    public ResponseEntity<?> getMyParticipations(Authentication authentication) {
        try {
            String email = authentication.getName();
            return new ResponseEntity<>(raceParticipationService.getParticipationsByOwner(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register-horse")
    public ResponseEntity<?> registerHorse(@RequestBody RegisterHorseRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            RaceParticipation participation = raceParticipationService.registerHorse(request, email);
            return new ResponseEntity<>(participation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{participationId}/register-jockey")
    public ResponseEntity<?> registerJockey(@PathVariable Integer participationId, @RequestBody RegisterJockeyRequest request, Authentication authentication) {
        try {
            String email = authentication.getName();
            RaceParticipation participation = raceParticipationService.registerJockey(participationId, request, email);
            return new ResponseEntity<>(participation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
