package com.horseracing.project3.controller;

import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.dto.response.UseCaseResponseDtos.RankingEntryResponse;
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
            return ResponseEntity.ok(new ApiResponse<>(true, "Ranking table updated", raceUseCaseService.updateRankingTable(tournamentId).stream().map(RankingEntryResponse::from).toList()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRankingTableAlias(@PathVariable Integer tournamentId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Ranking table updated", raceUseCaseService.updateRankingTable(tournamentId).stream().map(RankingEntryResponse::from).toList()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> viewRankingTable(@PathVariable Integer tournamentId) {
        var rankings = raceUseCaseService.viewRankingTable(tournamentId).stream().map(RankingEntryResponse::from).toList();
        String message = rankings.isEmpty() ? "Ranking table is empty" : "Ranking table loaded";
        return ResponseEntity.ok(new ApiResponse<>(true, message, rankings));
    }
}
