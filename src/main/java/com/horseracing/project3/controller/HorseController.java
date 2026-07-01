package com.horseracing.project3.controller;

import com.horseracing.project3.entity.RaceResult;
import com.horseracing.project3.service.HorseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/horses")
@CrossOrigin(origins = "*")
@Tag(name = "API Public Horse (History)")
public class HorseController {

    @Autowired
    private HorseService horseService;

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
