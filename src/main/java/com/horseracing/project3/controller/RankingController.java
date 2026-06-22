package com.horseracing.project3.controller;

import com.horseracing.project3.service.RaceUseCaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments/{tournamentId}/rankings")
@CrossOrigin(origins = "*")
@Tag(name = "API Ranking UC-29, UC-30")
public class RankingController {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @PostMapping("/recalculate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRankingTable(@PathVariable Integer tournamentId) {
        try {
            return ResponseEntity.ok(raceUseCaseService.updateRankingTable(tournamentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRankingTableAlias(@PathVariable Integer tournamentId) {
        try {
            return ResponseEntity.ok(raceUseCaseService.updateRankingTable(tournamentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> viewRankingTable(@PathVariable Integer tournamentId) {
        return ResponseEntity.ok(raceUseCaseService.viewRankingTable(tournamentId));
    }
}
