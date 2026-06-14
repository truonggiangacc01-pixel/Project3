package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.AssignRoleRequest;
import com.horseracing.project3.dto.request.CreateAccountRequest;
import com.horseracing.project3.dto.request.UpdateAccountRequest;
import com.horseracing.project3.dto.response.UserAccountResponse;
import com.horseracing.project3.enums.UserRole;
import com.horseracing.project3.service.AccountManagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/accounts")
@CrossOrigin(origins = "*")
@Tag(name = "API Admin")
public class AdminAccountController {

    @Autowired
    private AccountManagementService accountManagementService;

    @GetMapping
    public ResponseEntity<List<UserAccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountManagementService.getAllAccounts());
    }

    @PostMapping
    public ResponseEntity<UserAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        try {
            UserAccountResponse response = accountManagementService.createAccount(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{role}/{id}")
    public ResponseEntity<UserAccountResponse> updateAccount(@PathVariable UserRole role,
                                                             @PathVariable Integer id,
                                                             @RequestBody UpdateAccountRequest request) {
        try {
            UserAccountResponse response = accountManagementService.updateAccount(role, id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{role}/{id}/assign-role")
    public ResponseEntity<UserAccountResponse> assignRole(@PathVariable UserRole role,
                                                          @PathVariable Integer id,
                                                          @RequestBody AssignRoleRequest request) {
        try {
            UserAccountResponse response = accountManagementService.assignRole(role, id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{role}/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable UserRole role, @PathVariable Integer id) {
        try {
            accountManagementService.deleteAccount(role, id);
            return ResponseEntity.ok("Xóa tài khoản thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
