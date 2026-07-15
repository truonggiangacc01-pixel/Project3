package com.horseracing.project3.controller;

import com.horseracing.project3.dto.request.RegisterRequest;
import com.horseracing.project3.dto.response.RegisterResponse;
import com.horseracing.project3.service.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "API Register Kiểm thử thành công")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        try{

            // Xử lý nghiệp vụ đăng ký tài khoản
            registerService.register(registerRequest);

            // Trả về đối tượng RegisterResponse khi thành công
            return ResponseEntity.ok(new RegisterResponse(true, "Đăng ký tài khoản thành công!"));

        } catch (RuntimeException e) {

            // Trả về đối tượng RegisterResponse kèm thông điệp lỗi cụ thể khi thất bại
            return ResponseEntity.badRequest().body(new RegisterResponse(false, e.getMessage()));

        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", errorMessage);
        return ResponseEntity.badRequest().body(response);
    }
}
