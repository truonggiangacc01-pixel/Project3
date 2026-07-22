package com.horseracing.project3.controller;

import com.horseracing.project3.entity.RaceResult;
import com.horseracing.project3.enums.HorseHealthStatus;
import com.horseracing.project3.repository.HorseOwnerRepo;
import com.horseracing.project3.repository.HorseRepo;
import com.horseracing.project3.service.HorseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/horses", "/api/horses"})
@CrossOrigin(origins = "*")
@Tag(name = "API Public Horse (History) Kiểm thử thành công")
public class HorseController {

    @Autowired
    private HorseService horseService;

    @Autowired
    private HorseRepo horseRepo;

    @Autowired
    private HorseOwnerRepo horseOwnerRepo;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllHorses() {
        return ResponseEntity.ok(horseRepo.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getHorse(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(horseRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Horse not found")));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createHorse(@RequestBody com.horseracing.project3.entity.Horse horse) {
        try {
            if (horse.getHorseOwner() == null) {
                var owners = horseOwnerRepo.findAll();
                if (owners.isEmpty()) {
                    return new ResponseEntity<>("No HorseOwner exists in the database.", HttpStatus.BAD_REQUEST);
                }
                horse.setHorseOwner(owners.get(0));
            }
            if (horseRepo.existsByName(horse.getName())) {
                return new ResponseEntity<>("Tên ngựa này đã được dùng, vui lòng đặt tên khác", HttpStatus.BAD_REQUEST);
            }
            if (horse.getHealthStatus() == null) {
                horse.setHealthStatus(HorseHealthStatus.ELIGIBLE);
            }
            return new ResponseEntity<>(horseRepo.save(horse), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateHorseAdmin(@PathVariable Integer id, @RequestBody com.horseracing.project3.entity.Horse horseData) {
        try {
            com.horseracing.project3.entity.Horse existing = horseRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Horse not found"));

            if (!existing.getName().equalsIgnoreCase(horseData.getName())) {
                if (horseRepo.existsByName(horseData.getName())) {
                    return new ResponseEntity<>("Tên ngựa này đã được dùng, vui lòng đặt tên khác", HttpStatus.BAD_REQUEST);
                }
            }
            existing.setName(horseData.getName());
            existing.setAge(horseData.getAge());
            existing.setBreed(horseData.getBreed());
            if (horseData.getHealthStatus() != null) {
                existing.setHealthStatus(horseData.getHealthStatus());
            }
            return ResponseEntity.ok(horseRepo.save(existing));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteHorseAdmin(@PathVariable Integer id) {
        try {
            horseRepo.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{horseId}/history")
    public ResponseEntity<?> getHorseHistory(@PathVariable Integer horseId) {
        try {
            List<RaceResult> history = horseService.getHorsePerformanceHistory(horseId);
            // Returns empty list 200 OK if no history, satisfying Alternative Flow 1a
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
