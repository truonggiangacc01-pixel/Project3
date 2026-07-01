package com.horseracing.project3.service;

import com.horseracing.project3.entity.AuditLog;
import com.horseracing.project3.enums.AuditAction;
import com.horseracing.project3.repository.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepo auditLogRepo;

    /**
     * Ghi log bất đồng bộ (Asynchronous) để không block main thread.
     */
    @Async
    public void logActivity(Integer adminId, AuditAction action, String entityName, Integer entityId, Integer raceId, String details) {
        AuditLog log = new AuditLog(adminId, action, entityName, entityId, raceId, details);
        auditLogRepo.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepo.findAllByOrderByTimestampDesc();
    }
}
