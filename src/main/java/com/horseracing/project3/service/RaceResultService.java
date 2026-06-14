package com.horseracing.project3.service;

import com.horseracing.project3.entity.RaceResult;
import com.horseracing.project3.repository.RaceResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceResultService {

    @Autowired
    private RaceResultRepo raceResultRepo;

    public void saveRaceResult(RaceResult raceresult) {
        raceResultRepo.save(raceresult);
    }
}
