package com.horseracing.project3.entity;

import com.horseracing.project3.enums.RuleViolationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RuleViolation")
public class RuleViolation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String description;

    @Column(name = "penalty", columnDefinition = "NVARCHAR(255)")
    private String penalty;

    @Column(name = "evidence", columnDefinition = "NVARCHAR(MAX)")
    private String evidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RuleViolationStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "RaceScheduleId", nullable = false)
    private RaceSchedule raceSchedule;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public RuleViolationStatus getStatus() {
        return status;
    }

    public void setStatus(RuleViolationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public RaceSchedule getRaceSchedule() {
        return raceSchedule;
    }

    public void setRaceSchedule(RaceSchedule raceSchedule) {
        this.raceSchedule = raceSchedule;
    }
}
