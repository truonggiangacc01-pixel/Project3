package com.horseracing.project3.dto.request;

import com.horseracing.project3.enums.TournamentStatus;

import java.time.LocalDate;

public class UpdateTournamentRequestDto {
    @jakarta.validation.constraints.Size(min = 4, message = "Tên giải đấu phải có ít nhất 4 ký tự")
    private String name;
    @jakarta.validation.constraints.Size(min = 4, message = "Địa điểm phải có ít nhất 4 ký tự")
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private TournamentStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }
}
