package com.horseracing.project3.repository;

import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.entity.RaceParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horseracing.project3.enums.RaceParticipationStatus;
import com.horseracing.project3.enums.JockeyInvitationStatus;
import java.util.List;

@Repository
public interface RaceParticipationRepo extends JpaRepository<RaceParticipation, Integer> {
    boolean existsByHorseIdAndStatusIn(Integer horseId, List<RaceParticipationStatus> statuses);

    boolean existsByHorseIdAndRaceScheduleId(Integer horseId, Integer raceScheduleId);

    java.util.Optional<RaceParticipation> findByHorseIdAndRaceScheduleId(Integer horseId, Integer raceScheduleId);

    List<RaceParticipation> findByJockeyIdAndJockeyInvitationStatusNot(Integer jockeyId, JockeyInvitationStatus status);

    List<RaceParticipation> findByRaceScheduleId(Integer raceScheduleId);

    List<RaceParticipation> findByHorseHorseOwnerEmail(String email);

    List<RaceParticipation> findByJockeyEmail(String email);
}
