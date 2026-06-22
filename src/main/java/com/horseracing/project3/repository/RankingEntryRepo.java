package com.horseracing.project3.repository;

import com.horseracing.project3.entity.RankingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingEntryRepo extends JpaRepository<RankingEntry, Integer> {
    List<RankingEntry> findByTournamentIdOrderByRankPositionAsc(Integer tournamentId);
    Optional<RankingEntry> findByTournamentIdAndHorseId(Integer tournamentId, Integer horseId);
}
