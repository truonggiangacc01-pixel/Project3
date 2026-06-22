package com.horseracing.project3.repository;

import com.horseracing.project3.entity.RaceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceReportRepo extends JpaRepository<RaceReport, Integer> {
    boolean existsByRaceScheduleIdAndHasComplaintTrue(Integer raceScheduleId);
    List<RaceReport> findByRaceScheduleTournamentId(Integer tournamentId);
}
