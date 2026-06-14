package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.JockeyProfileUpdateRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.entity.Jockey;
import com.horseracing.project3.service.JockeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jockeys")
@CrossOrigin(origins = "*")
@Tag(name = "API Jockey")
public class JockeyController {

    @Autowired
    private JockeyService jockeyService;

    @PutMapping("/{id}/profile")
    public ResponseEntity<ApiResponse<Jockey>> updateProfile(
            @PathVariable("id") Integer id,
            @Valid @RequestBody JockeyProfileUpdateRequest request) {
        
        ApiResponse<Jockey> response = jockeyService.updateJockeyProfile(id, request);
        return ResponseEntity.ok(response);
    }
}
