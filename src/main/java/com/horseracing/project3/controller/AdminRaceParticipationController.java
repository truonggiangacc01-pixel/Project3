package com.horseracing.project3.controller;

import com.horseracing.project3.entity.RaceParticipation;
import com.horseracing.project3.service.RaceParticipationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/race-participations")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Participation for Admin")
public class AdminRaceParticipationController {

    @Autowired
    private RaceParticipationService raceParticipationService;

    @GetMapping
    public ResponseEntity<?> getAllParticipations() {
        try {
            return new ResponseEntity<>(raceParticipationService.getAllParticipations(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{participationId}/approve")
    public ResponseEntity<?> approveParticipation(@PathVariable Integer participationId) {
        try {
            RaceParticipation participation = raceParticipationService.approveParticipation(participationId);
            return new ResponseEntity<>(participation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{participationId}/reject")
    public ResponseEntity<?> rejectParticipation(@PathVariable Integer participationId) {
        try {
            RaceParticipation participation = raceParticipationService.rejectParticipation(participationId);
            return new ResponseEntity<>(participation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
