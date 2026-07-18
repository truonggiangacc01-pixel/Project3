package com.horseracing.project3.controller;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.service.HorseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/horses")
@CrossOrigin(origins = "*")
@Tag(name = "API Admin - Horses Kiểm thử thành công")
public class AdminHorseController {

    @Autowired
    private HorseService horseService;

    @GetMapping
    public ResponseEntity<?> getAllHorses() {
        try {
            List<Horse> horses = horseService.getAllHorses();
            return new ResponseEntity<>(horses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createHorse(@RequestBody Horse horse) {
        try {
            Horse savedHorse = horseService.addHorseAdmin(horse);
            return new ResponseEntity<>(savedHorse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHorseAdmin(@PathVariable Integer id, @RequestBody Horse horseData) {
        try {
            Horse updatedHorse = horseService.updateHorseAdmin(id, horseData);
            return ResponseEntity.ok(updatedHorse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHorseAdmin(@PathVariable Integer id) {
        try {
            horseService.deleteHorseAdmin(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
