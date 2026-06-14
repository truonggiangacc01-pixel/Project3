package com.horseracing.project3.service;

import com.horseracing.project3.entity.Spectator;
import com.horseracing.project3.repository.SpectatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpectatorService {

    @Autowired
    private SpectatorRepo spectatorRepo;

    public void saveSpectator(Spectator spectator) {
        spectatorRepo.save(spectator);
    }
}
