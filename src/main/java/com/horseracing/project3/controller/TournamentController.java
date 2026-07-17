package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.TournamentRequestDto;
import com.horseracing.project3.dto.response.TournamentResponseDto;
import com.horseracing.project3.dto.request.UpdateTournamentRequestDto;
import com.horseracing.project3.dto.request.CancelTournamentRequestDto;
import com.horseracing.project3.dto.request.OpenRegistrationRequestDto;
import com.horseracing.project3.repository.TournamentRepo;
import com.horseracing.project3.service.TournamentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments")
@CrossOrigin(origins = "*")
@Tag(name = "API Tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TournamentRepo tournamentRepo;

    @GetMapping
    public ResponseEntity<?> getAllTournaments() {
        try {
            var list = tournamentRepo.findAll().stream().map(t -> new TournamentResponseDto(
                    t.getId(),
                    t.getName(),
                    t.getLocation(),
                    t.getStartDate(),
                    t.getEndDate(),
                    t.getStatus() != null ? t.getStatus().name() : "DRAFT",
                    t.getRaceScheduleList() != null ? t.getRaceScheduleList().size() : 0
            )).toList();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // or hasRole('ADMIN') depending on how GrantedAuthority is set up. We'll check roles from JWT or assume hasAuthority('ROLE_ADMIN')
    public ResponseEntity<?> createTournament(@Valid @RequestBody TournamentRequestDto requestDto) {
        try {
            // Get currently logged-in Admin's email (which is the principal username in JWT)
            String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            
            TournamentResponseDto response = tournamentService.createTournament(requestDto, adminEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi hệ thống: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateTournament(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateTournamentRequestDto requestDto) {
        try {
            TournamentResponseDto response = tournamentService.updateTournament(id, requestDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi hệ thống: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> cancelTournament(
            @PathVariable Integer id,
            @Valid @RequestBody CancelTournamentRequestDto requestDto) {
        try {
            tournamentService.cancelTournament(id, requestDto);
            return ResponseEntity.ok("Hủy giải đấu thành công.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi hệ thống: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/registration")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> openRegistration(
            @PathVariable Integer id,
            @Valid @RequestBody OpenRegistrationRequestDto requestDto) {
        try {
            tournamentService.openRegistration(id, requestDto);
            return ResponseEntity.ok("Mở đăng ký giải đấu thành công.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi hệ thống: " + e.getMessage());
        }
    }
}
