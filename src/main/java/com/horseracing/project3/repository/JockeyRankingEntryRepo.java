package com.horseracing.project3.repository;

import com.horseracing.project3.entity.JockeyRankingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JockeyRankingEntryRepo extends JpaRepository<JockeyRankingEntry, Integer> {
    List<JockeyRankingEntry> findByTournamentIdOrderByRankPositionAsc(Integer tournamentId);
    Optional<JockeyRankingEntry> findByTournamentIdAndJockeyId(Integer tournamentId, Integer jockeyId);
}
