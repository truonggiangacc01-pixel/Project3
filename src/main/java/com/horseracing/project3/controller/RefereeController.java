package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.RefereeProfileUpdateRequest;
import com.horseracing.project3.dto.request.CertificateUpdateRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.entity.RaceReferee;
import com.horseracing.project3.service.RaceRefereeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/referees")
@CrossOrigin(origins = "*")
@Tag(name = "API Referee Kiểm thử thành công")
public class RefereeController {

    @Autowired
    private RaceRefereeService raceRefereeService;

    @PutMapping("/{id}/profile")
    @PreAuthorize("hasAnyAuthority('ROLE_RACE_REFEREE', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<RaceReferee>> updateProfile(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RefereeProfileUpdateRequest request) {
        
        ApiResponse<RaceReferee> response = raceRefereeService.updateRefereeProfile(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/certificate")
    @PreAuthorize("hasAnyAuthority('ROLE_RACE_REFEREE', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<RaceReferee>> updateCertificate(
            @PathVariable("id") Integer id,
            @Valid @RequestBody CertificateUpdateRequest request) {
            
        ApiResponse<RaceReferee> response = raceRefereeService.updateCertificate(id, request.getCertificateLevel());
        return ResponseEntity.ok(response);
    }
}
