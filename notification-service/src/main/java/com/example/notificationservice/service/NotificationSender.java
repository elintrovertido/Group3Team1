package com.example.notificationservice.service;

import com.example.notificationservice.entity.NotificationEntity;

public interface NotificationSender {
    void send(NotificationEntity notification) throws Exception;
} 
