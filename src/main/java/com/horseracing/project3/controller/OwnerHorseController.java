package com.horseracing.project3.controller;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.service.HorseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner/horses")
@CrossOrigin(origins = "*")
@Tag(name = "API HorseOwner")
public class OwnerHorseController {

    @Autowired
    private HorseService horseService;

    @GetMapping
    public ResponseEntity<?> getMyHorses(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Horse> horses = horseService.getHorsesByOwner(email);
            return new ResponseEntity<>(horses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addHorse(@RequestBody Horse horse, Authentication authentication) {
        try {
            String email = authentication.getName();
            Horse savedHorse = horseService.addHorse(horse, email);
            return new ResponseEntity<>(savedHorse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{horseId}")
    public ResponseEntity<?> updateHorse(@PathVariable Integer horseId, @RequestBody Horse horse, Authentication authentication) {
        try {
            String email = authentication.getName();
            Horse updatedHorse = horseService.updateHorse(horseId, horse, email);
            return new ResponseEntity<>(updatedHorse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
