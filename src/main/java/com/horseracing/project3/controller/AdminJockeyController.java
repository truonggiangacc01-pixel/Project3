package com.horseracing.project3.controller;

import com.horseracing.project3.entity.Jockey;
import com.horseracing.project3.service.JockeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/jockeys")
@CrossOrigin(origins = "*")
@Tag(name = "API Admin - Jockeys Kiểm thử thành công")
public class AdminJockeyController {

    @Autowired
    private JockeyService jockeyService;

    @GetMapping
    public ResponseEntity<?> getAllJockeys() {
        try {
            List<Jockey> jockeys = jockeyService.getAllJockeys();
            return new ResponseEntity<>(jockeys, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createJockey(@RequestBody Jockey jockey) {
        try {
            Jockey savedJockey = jockeyService.addJockeyAdmin(jockey);
            return new ResponseEntity<>(savedJockey, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJockeyAdmin(@PathVariable Integer id, @RequestBody Jockey jockeyData) {
        try {
            Jockey updatedJockey = jockeyService.updateJockeyAdmin(id, jockeyData);
            return ResponseEntity.ok(updatedJockey);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJockeyAdmin(@PathVariable Integer id) {
        try {
            jockeyService.deleteJockeyAdmin(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
