package com.horseracing.project3.controller;

import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horses")
@CrossOrigin(origins = "*")
@Tag(name = "API Horse Search UC-35")
public class HorseSearchController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @GetMapping("/search")
    public ResponseEntity<?> searchHorseInformation(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(raceUseCaseService.searchHorseInformation(keyword));
    }
}
