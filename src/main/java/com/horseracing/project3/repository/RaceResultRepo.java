package com.horseracing.project3.repository;

import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.entity.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceResultRepo extends JpaRepository<RaceResult, Integer> {
    List<RaceResult> findByRaceScheduleId(Integer raceScheduleId);

    @Query("SELECT r FROM RaceResult r WHERE r.raceParticipation.horse.id = :horseId")
    List<RaceResult> findByHorseId(@Param("horseId") Integer horseId);
}
