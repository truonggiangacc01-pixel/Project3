package com.horseracing.project3.service;

import com.horseracing.project3.dto.request.CreateRaceScheduleRequest;
import com.horseracing.project3.entity.RaceSchedule;
import com.horseracing.project3.entity.Tournament;
import com.horseracing.project3.enums.RaceScheduleStatus;
import com.horseracing.project3.enums.TournamentStatus;
import com.horseracing.project3.repository.RaceScheduleRepo;
import com.horseracing.project3.repository.TournamentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceScheduleService {

    @Autowired
    private RaceScheduleRepo raceScheduleRepo;

    @Autowired
    private TournamentRepo tournamentRepo;

    public void saveRaceSchedule(RaceSchedule raceschedule) {
        raceScheduleRepo.save(raceschedule);
    }

    public RaceSchedule createRaceSchedule(Integer tournamentId, CreateRaceScheduleRequest request) {
        Tournament tournament = tournamentRepo.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        if (tournament.getStatus() == TournamentStatus.COMPLETED || tournament.getStatus() == TournamentStatus.CANCELLED) {
            throw new RuntimeException("Cannot create Race Schedule for a COMPLETED or CANCELLED tournament");
        }

        if (request.getRaceDate().isBefore(tournament.getStartDate()) || request.getRaceDate().isAfter(tournament.getEndDate())) {
            throw new RuntimeException("Race date must be within the tournament's start and end dates");
        }

        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().isEqual(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        if (!request.getStartTime().toLocalDate().equals(request.getRaceDate()) || !request.getEndTime().toLocalDate().equals(request.getRaceDate())) {
            throw new RuntimeException("Start time and end time must be on the race date");
        }

        RaceSchedule schedule = new RaceSchedule();
        schedule.setName(request.getName());
        schedule.setRaceDate(request.getRaceDate());
        schedule.setLocation(request.getLocation());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setStatus(RaceScheduleStatus.PENDING);
        schedule.setTournament(tournament);

        return raceScheduleRepo.save(schedule);
    }

    @Autowired
    private com.horseracing.project3.repository.RaceParticipationRepo raceParticipationRepo;

    public RaceSchedule updateSchedule(Integer scheduleId, com.horseracing.project3.dto.request.ScheduleRequestDto request) {
        RaceSchedule schedule = raceScheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("RaceSchedule not found"));

        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new RuntimeException("StartTime and EndTime are required");
        }

        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().isEqual(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        // Validate participations
        java.util.List<Integer> partIds = request.getParticipationIds();
        if (partIds != null && !partIds.isEmpty()) {
            java.util.List<com.horseracing.project3.entity.RaceParticipation> participations = raceParticipationRepo.findAllById(partIds);
            
            if (participations.size() != partIds.size()) {
                throw new RuntimeException("One or more RaceParticipations not found");
            }

            java.util.Set<Integer> horseIds = new java.util.HashSet<>();

            for (com.horseracing.project3.entity.RaceParticipation p : participations) {
                // Validate FK-17: Only CONFIRMED allowed
                if (p.getStatus() != com.horseracing.project3.enums.RaceParticipationStatus.CONFIRMED) {
                    throw new com.horseracing.project3.exception.InvalidParticipationStatusException(
                            "Participation " + p.getId() + " is not CONFIRMED. Only CONFIRMED registrations are allowed.");
                }

                // Validate FK-25: Duplicate horse
                if (!horseIds.add(p.getHorse().getId())) {
                    throw new com.horseracing.project3.exception.DuplicateHorseException(
                            "Duplicate horse detected in the same race. Horse ID: " + p.getHorse().getId());
                }

                // Validate FK-24: Jockey schedule conflict
                if (p.getJockey() != null) {
                    long overlapping = raceScheduleRepo.countOverlappingSchedulesForJockey(
                            p.getJockey().getId(), scheduleId, request.getStartTime(), request.getEndTime()
                    );
                    if (overlapping > 0) {
                        throw new com.horseracing.project3.exception.JockeyScheduleConflictException(
                                "Jockey " + p.getJockey().getId() + " has a schedule conflict.");
                    }
                }
            }

            // Assign participations to schedule
            for (com.horseracing.project3.entity.RaceParticipation p : participations) {
                p.setRaceSchedule(schedule);
                raceParticipationRepo.save(p);
            }
        }

        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());

        return raceScheduleRepo.save(schedule);
    }
}
