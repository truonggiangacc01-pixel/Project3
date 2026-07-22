package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.CancelPredictionRequest;
import com.horseracing.project3.dto.request.PredictionRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.PredictionResponse;
import com.horseracing.project3.service.PredictionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "*")
@Tag(name = "API Predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SPECTATOR')")
    public ResponseEntity<?> createPrediction(@RequestBody PredictionRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Prediction created", PredictionResponse.from(predictionService.createPrediction(request))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/spectators/{spectatorId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SPECTATOR','ROLE_ADMIN')")
    public ResponseEntity<?> getPredictionHistory(@PathVariable Integer spectatorId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Prediction history loaded",
                    predictionService.getPredictionHistory(spectatorId).stream().map(PredictionResponse::from).toList()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{predictionId}/cancel")
    @PreAuthorize("hasAnyAuthority('ROLE_SPECTATOR','ROLE_ADMIN')")
    public ResponseEntity<?> cancelPrediction(
            @PathVariable Integer predictionId,
            @RequestBody CancelPredictionRequest request) {
        try {
            Integer spectatorId = request == null ? null : request.getSpectatorId();
            return ResponseEntity.ok(new ApiResponse<>(true, "Prediction cancelled and refunded",
                    PredictionResponse.from(predictionService.cancelPrediction(predictionId, spectatorId))));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
