package com.horseracing.project3.controller;

import com.horseracing.project3.entity.AuditLog;
import com.horseracing.project3.service.AuditLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/audit-logs")
@CrossOrigin(origins = "*")
@Tag(name = "API Audit Log (Admin)")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<?> getAuditLogs() {
        try {
            List<AuditLog> logs = auditLogService.getAllLogs();
            // Xử lý Alternative Flow 1a: Trả về danh sách rỗng nếu không có log nào
            return new ResponseEntity<>(logs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
