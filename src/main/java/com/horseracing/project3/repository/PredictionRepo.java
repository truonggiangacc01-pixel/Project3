package com.horseracing.project3.repository;

import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepo extends JpaRepository<Prediction, Integer> {
    List<Prediction> findByRaceScheduleId(Integer raceScheduleId);
}
