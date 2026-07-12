package com.horseracing.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PredictionScheduler {

    @Autowired
    private RaceUseCaseService raceUseCaseService;

    @Scheduled(fixedDelay = 60000)
    public void closeDuePredictions() {
        raceUseCaseService.closeDuePredictions();
    }
}
