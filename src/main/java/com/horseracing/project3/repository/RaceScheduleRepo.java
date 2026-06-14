package com.horseracing.project3.repository;

import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.entity.RaceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

@Repository
public interface RaceScheduleRepo extends JpaRepository<RaceSchedule, Integer> {

    @Query("SELECT COUNT(rs) FROM RaceSchedule rs JOIN rs.raceParticipationList rp " +
           "WHERE rp.jockey.id = :jockeyId " +
           "AND rs.id != :currentScheduleId " +
           "AND (rs.startTime < :endTime AND rs.endTime > :startTime)")
    long countOverlappingSchedulesForJockey(@Param("jockeyId") Integer jockeyId, 
                                            @Param("currentScheduleId") Integer currentScheduleId, 
                                            @Param("startTime") LocalDateTime startTime, 
                                            @Param("endTime") LocalDateTime endTime);
}
