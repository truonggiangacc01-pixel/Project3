package com.horseracing.project3.entity;

import com.horseracing.project3.enums.RaceReportStatus;
import com.horseracing.project3.enums.RaceReportType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RaceReport")
public class RaceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private RaceReportType reportType;

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String content;

    @Column(name = "violation_note", columnDefinition = "NVARCHAR(MAX)")
    private String violationNote;

    @Column(name = "has_complaint", nullable = false)
    private Boolean hasComplaint = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RaceReportStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "RaceScheduleId", nullable = false)
    private RaceSchedule raceSchedule;

    @ManyToOne
    @JoinColumn(name = "RaceRefereeId")
    private RaceReferee raceReferee;

    public Integer getId() {
        return id;
    }

    public RaceReportType getReportType() {
        return reportType;
    }

    public void setReportType(RaceReportType reportType) {
        this.reportType = reportType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getViolationNote() {
        return violationNote;
    }

    public void setViolationNote(String violationNote) {
        this.violationNote = violationNote;
    }

    public Boolean getHasComplaint() {
        return hasComplaint;
    }

    public void setHasComplaint(Boolean hasComplaint) {
        this.hasComplaint = hasComplaint;
    }

    public RaceReportStatus getStatus() {
        return status;
    }

    public void setStatus(RaceReportStatus status) {
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

    public RaceReferee getRaceReferee() {
        return raceReferee;
    }

    public void setRaceReferee(RaceReferee raceReferee) {
        this.raceReferee = raceReferee;
    }
}
