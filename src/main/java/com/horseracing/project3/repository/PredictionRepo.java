package com.horseracing.project3.repository;

import com.horseracing.project3.entity.Prediction;
import com.horseracing.project3.enums.PredictionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepo extends JpaRepository<Prediction, Integer> {
    List<Prediction> findByRaceScheduleId(Integer raceScheduleId);
    List<Prediction> findBySpectatorIdOrderByCreatedAtDesc(Integer spectatorId);
    boolean existsBySpectatorIdAndRaceScheduleIdAndStatusIn(Integer spectatorId, Integer raceScheduleId, List<PredictionStatus> statuses);
}
