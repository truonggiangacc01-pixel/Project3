package com.horseracing.project3.repository;

import com.horseracing.project3.entity.RuleViolation;
import com.horseracing.project3.enums.RuleViolationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleViolationRepo extends JpaRepository<RuleViolation, Integer> {
    boolean existsByRaceScheduleIdAndStatus(Integer raceScheduleId, RuleViolationStatus status);
}
