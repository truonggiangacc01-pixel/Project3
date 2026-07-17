package com.horseracing.project3.dto.response;

import java.time.LocalDate;

public class TournamentResponseDto {
    private Integer id;
    private String name;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private int races;

    public TournamentResponseDto() {
    }

    public TournamentResponseDto(Integer id, String name, String location, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TournamentResponseDto(Integer id, String name, String location, LocalDate startDate, LocalDate endDate, String status, int races) {
        this(id, name, location, startDate, endDate);
        this.status = status;
        this.races = races;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRaces() {
        return races;
    }

    public void setRaces(int races) {
        this.races = races;
    }
}
