package com.horseracing.project3.service;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.enums.RaceParticipationStatus;
import com.horseracing.project3.repository.HorseOwnerRepo;
import com.horseracing.project3.repository.HorseRepo;
import com.horseracing.project3.repository.RaceParticipationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HorseService {

    @Autowired
    private HorseRepo horseRepo;

    @Autowired
    private HorseOwnerRepo horseOwnerRepo;

    @Autowired
    private RaceParticipationRepo raceParticipationRepo;

    public void saveHorse(Horse horse) {
        horseRepo.save(horse);
    }

    public Horse addHorse(Horse newHorse, String ownerEmail) {
        if (horseRepo.existsByName(newHorse.getName())) {
            throw new RuntimeException("Tên ngựa này đã được dùng, vui lòng đặt tên khác");
        }

        HorseOwner owner = horseOwnerRepo.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        
        newHorse.setHorseOwner(owner);
        owner.addHorse(newHorse);
        
        return horseRepo.save(newHorse);
    }

    public boolean isHorseLocked(Integer horseId) {
        List<RaceParticipationStatus> activeStatuses = Arrays.asList(
                RaceParticipationStatus.PENDING, 
                RaceParticipationStatus.CONFIRMED
        );
        return raceParticipationRepo.existsByHorseIdAndStatusIn(horseId, activeStatuses);
    }

    public Horse updateHorse(Integer horseId, Horse updatedHorseData, String ownerEmail) {
        Horse existingHorse = horseRepo.findById(horseId)
                .orElseThrow(() -> new RuntimeException("Horse not found"));

        if (!existingHorse.getHorseOwner().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("You do not own this horse");
        }

        if (isHorseLocked(horseId)) {
            throw new RuntimeException("DataLockingException: Cannot edit horse information while it has Pending or Confirmed race registrations.");
        }

        if (!existingHorse.getName().equalsIgnoreCase(updatedHorseData.getName())) {
            if (horseRepo.existsByName(updatedHorseData.getName())) {
                throw new RuntimeException("Tên ngựa này đã được dùng, vui lòng đặt tên khác");
            }
        }

        existingHorse.setName(updatedHorseData.getName());
        existingHorse.setAge(updatedHorseData.getAge());
        existingHorse.setBreed(updatedHorseData.getBreed());
        existingHorse.setHealthStatus(updatedHorseData.getHealthStatus());

        return horseRepo.save(existingHorse);
    }

    public List<Horse> getHorsesByOwner(String ownerEmail) {
        return horseRepo.findByHorseOwner_Email(ownerEmail);
    }
}
