package com.horseracing.project3.entity;

import com.horseracing.project3.enums.AuditAction;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "AuditLog", indexes = {
        @Index(name = "idx_auditlog_timestamp", columnList = "timestamp"),
        @Index(name = "idx_auditlog_admin", columnList = "admin_id")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "admin_id", nullable = false)
    private Integer adminId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private AuditAction action;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "entity_id")
    private Integer entityId;

    // BR-31: Bắt buộc phải có liên kết race_id khi thao tác trên entity có liên quan
    @Column(name = "race_id")
    private Integer raceId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    public AuditLog() {
    }

    public AuditLog(Integer adminId, AuditAction action, String entityName, Integer entityId, Integer raceId, String details) {
        this.adminId = adminId;
        this.action = action;
        this.entityName = entityName;
        this.entityId = entityId;
        this.raceId = raceId;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
