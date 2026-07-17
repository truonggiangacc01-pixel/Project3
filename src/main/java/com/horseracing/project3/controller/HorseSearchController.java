package com.horseracing.project3.controller;

import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.HorseSearchResponse;
import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horses")
@CrossOrigin(origins = "*")
@Tag(name = "API Horse Search UC-35 Kiểm thử thành công")
public class HorseSearchController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @GetMapping("/search")
    public ResponseEntity<?> searchHorseInformation(@RequestParam(required = false) String keyword) {
        var horses = raceUseCaseService.searchHorseInformation(keyword).stream().map(HorseSearchResponse::from).toList();
        String message = horses.isEmpty() ? "No horse information found" : "Horse information loaded";
        return ResponseEntity.ok(new ApiResponse<>(true, message, horses));
    }
}
