package com.horseracing.project3.service;

import com.horseracing.project3.entity.Admin;
import com.horseracing.project3.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public void saveAdmin(Admin admin) {
        adminRepo.save(admin);
    }
}
