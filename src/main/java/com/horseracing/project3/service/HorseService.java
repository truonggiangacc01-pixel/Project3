package com.horseracing.project3.service;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.enums.HorseHealthStatus;
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
        if (newHorse.getName() == null || newHorse.getName().trim().length() < 4) {
            throw new RuntimeException("Tên ngựa phải có ít nhất 4 ký tự");
        }
        if (newHorse.getBreed() == null || newHorse.getBreed().trim().length() < 4) {
            throw new RuntimeException("Giống ngựa phải có ít nhất 4 ký tự");
        }
        if (newHorse.getBreed().matches(".*\\d.*")) {
            throw new RuntimeException("Giống ngựa chỉ chấp nhận chữ cái");
        }
        if (horseRepo.existsByName(newHorse.getName())) {
            throw new RuntimeException("Tên ngựa này đã được dùng, vui lòng đặt tên khác");
        }

        HorseOwner owner = horseOwnerRepo.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (newHorse.getHealthStatus() == null) {
            newHorse.setHealthStatus(HorseHealthStatus.ELIGIBLE);
        }
        
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
        
        if (updatedHorseData.getName() == null || updatedHorseData.getName().trim().length() < 4) {
            throw new RuntimeException("Tên ngựa phải có ít nhất 4 ký tự");
        }
        if (updatedHorseData.getBreed() == null || updatedHorseData.getBreed().trim().length() < 4) {
            throw new RuntimeException("Giống ngựa phải có ít nhất 4 ký tự");
        }
        if (updatedHorseData.getBreed().matches(".*\\d.*")) {
            throw new RuntimeException("Giống ngựa chỉ chấp nhận chữ cái");
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

    @Autowired
    private com.horseracing.project3.repository.RaceResultRepo raceResultRepo;

    public List<com.horseracing.project3.entity.RaceResult> getHorsePerformanceHistory(Integer horseId) {
        if (!horseRepo.existsById(horseId)) {
            throw new RuntimeException("Horse not found. The provided horse ID does not exist in the system.");
        }
        
        // Return empty list if horse exists but has no races (Alternative Flow 1a)
        return raceResultRepo.findByHorseId(horseId);
    }

    public List<Horse> getAllHorses() {
        return horseRepo.findAll();
    }

    public Horse addHorseAdmin(Horse newHorse) {
        if (newHorse.getName() == null || newHorse.getName().trim().length() < 4) {
            throw new RuntimeException("Tên ngựa phải có ít nhất 4 ký tự");
        }
        if (newHorse.getBreed() == null || newHorse.getBreed().trim().length() < 4) {
            throw new RuntimeException("Giống ngựa phải có ít nhất 4 ký tự");
        }
        if (newHorse.getBreed().matches(".*\\d.*")) {
            throw new RuntimeException("Giống ngựa chỉ chấp nhận chữ cái");
        }
        if (horseRepo.existsByName(newHorse.getName())) {
            throw new RuntimeException("Tên ngựa này đã được dùng, vui lòng đặt tên khác");
        }

        if (newHorse.getHorseOwner() != null && newHorse.getHorseOwner().getFullName() != null && !newHorse.getHorseOwner().getFullName().trim().isEmpty()) {
            String ownerName = newHorse.getHorseOwner().getFullName().trim();
            HorseOwner foundOwner = horseOwnerRepo.findFirstByFullName(ownerName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ sở hữu với tên: " + ownerName));
            newHorse.setHorseOwner(foundOwner);
        } else {
            List<HorseOwner> owners = horseOwnerRepo.findAll();
            if (owners.isEmpty()) {
                throw new RuntimeException("No HorseOwner exists in the database.");
            }
            newHorse.setHorseOwner(owners.get(0));
        }
        
        if (newHorse.getHealthStatus() == null) {
            newHorse.setHealthStatus(com.horseracing.project3.enums.HorseHealthStatus.ELIGIBLE);
        }
        
        return horseRepo.save(newHorse);
    }

    public Horse updateHorseAdmin(Integer horseId, Horse updatedHorseData) {
        Horse existingHorse = horseRepo.findById(horseId)
                .orElseThrow(() -> new RuntimeException("Horse not found"));

        if (!existingHorse.getName().equalsIgnoreCase(updatedHorseData.getName())) {
            if (horseRepo.existsByName(updatedHorseData.getName())) {
                throw new RuntimeException("Tên ngựa này đã được dùng, vui lòng đặt tên khác");
            }
        }
        
        if (updatedHorseData.getName() == null || updatedHorseData.getName().trim().length() < 4) {
            throw new RuntimeException("Tên ngựa phải có ít nhất 4 ký tự");
        }
        if (updatedHorseData.getBreed() == null || updatedHorseData.getBreed().trim().length() < 4) {
            throw new RuntimeException("Giống ngựa phải có ít nhất 4 ký tự");
        }
        if (updatedHorseData.getBreed().matches(".*\\d.*")) {
            throw new RuntimeException("Giống ngựa chỉ chấp nhận chữ cái");
        }

        existingHorse.setName(updatedHorseData.getName());
        existingHorse.setAge(updatedHorseData.getAge());
        existingHorse.setBreed(updatedHorseData.getBreed());
        if (updatedHorseData.getHealthStatus() != null) {
            existingHorse.setHealthStatus(updatedHorseData.getHealthStatus());
        }

        if (updatedHorseData.getHorseOwner() != null && updatedHorseData.getHorseOwner().getFullName() != null && !updatedHorseData.getHorseOwner().getFullName().trim().isEmpty()) {
            String ownerName = updatedHorseData.getHorseOwner().getFullName().trim();
            HorseOwner foundOwner = horseOwnerRepo.findFirstByFullName(ownerName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ sở hữu với tên: " + ownerName));
            existingHorse.setHorseOwner(foundOwner);
        }

        return horseRepo.save(existingHorse);
    }

    public void deleteHorseAdmin(Integer horseId) {
        if (!horseRepo.existsById(horseId)) {
            throw new RuntimeException("Horse not found");
        }
        horseRepo.deleteById(horseId);
    }

    public void deleteHorseOwner(Integer horseId, String ownerEmail) {
        Horse existingHorse = horseRepo.findById(horseId)
                .orElseThrow(() -> new RuntimeException("Horse not found"));

        if (!existingHorse.getHorseOwner().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("You do not own this horse");
        }

        if (isHorseLocked(horseId)) {
            throw new RuntimeException("DataLockingException: Cannot delete horse information while it has Pending or Confirmed race registrations.");
        }

        horseRepo.deleteById(horseId);
    }
}
