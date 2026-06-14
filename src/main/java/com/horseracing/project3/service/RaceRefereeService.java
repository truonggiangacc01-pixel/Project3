package com.horseracing.project3.service;

import com.horseracing.project3.entity.RaceReferee;
import com.horseracing.project3.repository.RaceRefereeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceRefereeService {

    @Autowired
    private RaceRefereeRepo raceRefereeRepo;

    public void saveRaceReferee(RaceReferee racereferee) {
        raceRefereeRepo.save(racereferee);
    }
}
