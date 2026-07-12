package com.horseracing.project3.dto.request;

import java.math.BigDecimal;

public class PredictionRequest {
    private Integer spectatorId;
    private Integer raceScheduleId;
    private Integer horseId;
    private BigDecimal stakeAmount;

    public Integer getSpectatorId() {
        return spectatorId;
    }

    public void setSpectatorId(Integer spectatorId) {
        this.spectatorId = spectatorId;
    }

    public Integer getRaceScheduleId() {
        return raceScheduleId;
    }

    public void setRaceScheduleId(Integer raceScheduleId) {
        this.raceScheduleId = raceScheduleId;
    }

    public Integer getHorseId() {
        return horseId;
    }

    public void setHorseId(Integer horseId) {
        this.horseId = horseId;
    }

    public BigDecimal getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(BigDecimal stakeAmount) {
        this.stakeAmount = stakeAmount;
    }
}
