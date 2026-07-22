package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.UseCaseRequestDtos.SendNotificationRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.NotificationResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.RankingEntryResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.RaceScheduleResponse;
import com.horseracing.project3.service.RaceUseCaseService;
import com.horseracing.project3.service.RaceRefereeService;
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

    @Autowired
    private RaceRefereeService raceRefereeService;

    @GetMapping("/referees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllReferees() {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách trọng tài thành công", raceRefereeService.getAllReferees()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/races/{raceId}/assign-referee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> assignRefereeToRace(@PathVariable Integer raceId, @RequestParam(required = false) Integer refereeId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Phân công trọng tài thành công", RaceScheduleResponse.from(raceUseCaseService.assignRefereeToRace(raceId, refereeId))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/predictions/close-due")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> closeDuePredictions() {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Due predictions closed", raceUseCaseService.closeDuePredictions()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/predictions/settle/{raceId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> settleRacePredictions(@PathVariable Integer raceId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Race predictions settled", raceUseCaseService.settleRacePredictions(raceId)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/notifications")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> sendNotification(@RequestBody SendNotificationRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Notification sent", NotificationResponse.from(raceUseCaseService.sendNotification(request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/rankings/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRankingTable(@RequestParam Integer tournamentId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Ranking table updated", raceUseCaseService.updateRankingTable(tournamentId).stream().map(RankingEntryResponse::from).toList()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
