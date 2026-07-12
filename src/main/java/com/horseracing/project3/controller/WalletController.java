package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.WalletRequest;
import com.horseracing.project3.dto.response.ApiResponse;
import com.horseracing.project3.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@CrossOrigin(origins = "*")
@Tag(name = "API Wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/spectators/{spectatorId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SPECTATOR','ROLE_ADMIN')")
    public ResponseEntity<?> getWallet(@PathVariable Integer spectatorId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Wallet loaded", walletService.getWallet(spectatorId)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/spectators/{spectatorId}/deposit")
    @PreAuthorize("hasAnyAuthority('ROLE_SPECTATOR','ROLE_ADMIN')")
    public ResponseEntity<?> deposit(@PathVariable Integer spectatorId, @RequestBody WalletRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Wallet deposited", walletService.deposit(spectatorId, request.getAmount(), request.getGateway())));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/spectators/{spectatorId}/withdraw")
    @PreAuthorize("hasAnyAuthority('ROLE_SPECTATOR','ROLE_ADMIN')")
    public ResponseEntity<?> withdraw(@PathVariable Integer spectatorId, @RequestBody WalletRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Wallet withdrawn", walletService.withdraw(spectatorId, request.getAmount(), request.getGateway())));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/spectators/{spectatorId}/transactions")
    @PreAuthorize("hasAnyAuthority('ROLE_SPECTATOR','ROLE_ADMIN')")
    public ResponseEntity<?> getTransactions(@PathVariable Integer spectatorId) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Wallet transactions loaded", walletService.getTransactions(spectatorId)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
