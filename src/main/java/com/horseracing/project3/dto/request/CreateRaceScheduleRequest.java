package com.horseracing.project3.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.horseracing.project3.enums.RaceScheduleStatus;

public class CreateRaceScheduleRequest {
    private String name;
    private LocalDate raceDate;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RaceScheduleStatus status;

    public CreateRaceScheduleRequest() {}

    public CreateRaceScheduleRequest(String name, LocalDate raceDate, String location, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.raceDate = raceDate;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public RaceScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(RaceScheduleStatus status) {
        this.status = status;
    }
}
