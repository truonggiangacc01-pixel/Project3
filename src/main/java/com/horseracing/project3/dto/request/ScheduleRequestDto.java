package com.horseracing.project3.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.horseracing.project3.enums.RaceScheduleStatus;

public class ScheduleRequestDto {

    private String name;
    private LocalDate raceDate;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> participationIds;
    private RaceScheduleStatus status;

    public ScheduleRequestDto() {
    }

    public ScheduleRequestDto(LocalDateTime startTime, LocalDateTime endTime, List<Integer> participationIds) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.participationIds = participationIds;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getParticipationIds() {
        return participationIds;
    }

    public void setParticipationIds(List<Integer> participationIds) {
        this.participationIds = participationIds;
    }

    public RaceScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(RaceScheduleStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(LocalDate raceDate) {
        this.raceDate = raceDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
