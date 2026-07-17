package com.horseracing.project3.controller;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.service.HorseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
