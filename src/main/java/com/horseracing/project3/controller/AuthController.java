package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.LoginRequest;
import com.horseracing.project3.dto.response.LoginResponse;
import com.horseracing.project3.service.AuthService;
import com.horseracing.project3.dto.request.ForgotPasswordRequest;
import com.horseracing.project3.dto.request.ResetPasswordRequest;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "API Authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new LoginResponse(false, e.getMessage(), null, null, null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(){
        // Trong hệ thống dùng JWT cơ bản, Backend không cần làm gì nhiều.
        // Việc xóa token sẽ do Frontend thực hiện.
        // Tuy nhiên, ta vẫn trả về một thông báo thành công để Frontend biết và xử lý tiếp.
        return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Đăng xuất thành công! Vui lòng xóa Token ở phía Client.\"}");
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            authService.forgotPassword(request.getEmail());
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Mã OTP đã được gửi đến email của bạn!\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Đặt lại mật khẩu thành công!\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

}
