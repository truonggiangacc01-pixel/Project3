package com.horseracing.project3.dto.response;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.entity.Prediction;
import com.horseracing.project3.entity.RaceSchedule;
import com.horseracing.project3.entity.Spectator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PredictionResponse(
        Integer id,
        Integer spectatorId,
        String spectatorName,
        Integer raceScheduleId,
        String raceName,
        Integer horseId,
        String horseName,
        BigDecimal stakeAmount,
        BigDecimal payoutAmount,
        String status,
        LocalDateTime createdAt
) {
    public static PredictionResponse from(Prediction prediction) {
        Spectator spectator = prediction.getSpectator();
        RaceSchedule race = prediction.getRaceSchedule();
        Horse horse = prediction.getHorse();
        return new PredictionResponse(
                prediction.getId(),
                spectator == null ? null : spectator.getId(),
                spectator == null ? null : spectator.getFullName(),
                race == null ? null : race.getId(),
                race == null ? null : race.getName(),
                horse == null ? null : horse.getId(),
                horse == null ? null : horse.getName(),
                prediction.getStakeAmount(),
                prediction.getPayoutAmount(),
                prediction.getStatus() == null ? null : prediction.getStatus().name(),
                prediction.getCreatedAt()
        );
    }
}
