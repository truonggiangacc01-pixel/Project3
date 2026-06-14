package com.horseracing.project3.service;

import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.repository.HorseOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorseOwnerService {

    @Autowired
    private HorseOwnerRepo horseOwnerRepo;

    public void saveHorseOwner(HorseOwner horseOwner) {
        horseOwnerRepo.save(horseOwner);
    }
}
