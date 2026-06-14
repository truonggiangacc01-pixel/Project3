package com.horseracing.project3.service;

import com.horseracing.project3.entity.Prize;
import com.horseracing.project3.repository.PrizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrizeService {

    @Autowired
    private PrizeRepo prizeRepo;

    public void savePrize(Prize prize) {
        prizeRepo.save(prize);
    }
}
