package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.CreateRaceScheduleRequest;
import com.horseracing.project3.entity.RaceSchedule;
import com.horseracing.project3.enums.RaceScheduleStatus;
import com.horseracing.project3.repository.RaceScheduleRepo;
import com.horseracing.project3.service.RaceScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}/race-schedules")
@CrossOrigin(origins = "*")
@Tag(name = "API Race Schedule")
public class RaceScheduleController {

    @Autowired
    private RaceScheduleService raceScheduleService;

    @Autowired
    private RaceScheduleRepo raceScheduleRepo;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createRaceSchedule(@PathVariable Integer tournamentId, @RequestBody CreateRaceScheduleRequest request, Authentication authentication) {
        try {
            // Note: Security configuration or interceptor should verify if the user has ADMIN role.
            RaceSchedule schedule = raceScheduleService.createRaceSchedule(tournamentId, request);
            return new ResponseEntity<>(schedule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{scheduleId}/schedule")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateSchedule(@PathVariable Integer tournamentId, @PathVariable Integer scheduleId, @RequestBody com.horseracing.project3.dto.request.ScheduleRequestDto request, Authentication authentication) {
        try {
            // Note: Security configuration or interceptor should verify if the user has ADMIN role.
            RaceSchedule schedule = raceScheduleService.updateSchedule(scheduleId, request);
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{scheduleId}/cancel")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> cancelSchedule(@PathVariable Integer tournamentId, @PathVariable Integer scheduleId) {
        try {
            RaceSchedule schedule = raceScheduleRepo.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("RaceSchedule not found"));
            schedule.setStatus(RaceScheduleStatus.CANCELLED);
            return ResponseEntity.ok(raceScheduleRepo.save(schedule));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
