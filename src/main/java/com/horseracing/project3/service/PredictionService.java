package com.horseracing.project3.service;

import com.horseracing.project3.entity.Prediction;
import com.horseracing.project3.repository.PredictionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepo predictionRepo;

    public void savePrediction(Prediction prediction) {
        predictionRepo.save(prediction);
    }
}
