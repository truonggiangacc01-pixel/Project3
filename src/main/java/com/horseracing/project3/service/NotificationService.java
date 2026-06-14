package com.horseracing.project3.service;

import com.horseracing.project3.entity.Notification;
import com.horseracing.project3.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    public void saveNotification(Notification notification) {
        notificationRepo.save(notification);
    }
}
