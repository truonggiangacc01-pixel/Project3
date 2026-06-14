package com.horseracing.project3.dto.request;

public class RegisterHorseRequest {
    private Integer horseId;
    private Integer raceScheduleId;

    public RegisterHorseRequest() {}

    public RegisterHorseRequest(Integer horseId, Integer raceScheduleId) {
        this.horseId = horseId;
        this.raceScheduleId = raceScheduleId;
    }

    public Integer getHorseId() {
        return horseId;
    }

    public void setHorseId(Integer horseId) {
        this.horseId = horseId;
    }

    public Integer getRaceScheduleId() {
        return raceScheduleId;
    }

    public void setRaceScheduleId(Integer raceScheduleId) {
        this.raceScheduleId = raceScheduleId;
    }
}
