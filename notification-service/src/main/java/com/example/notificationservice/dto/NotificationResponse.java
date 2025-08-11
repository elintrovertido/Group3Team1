package com.example.notificationservice.dto;

import com.example.notificationservice.entity.NotificationType;
import com.example.notificationservice.entity.Priority;
import com.example.notificationservice.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private String recipient;
    private String messageContent;
    private NotificationType type;
    private Priority priority;
    private Status status;
    private LocalDateTime scheduledTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 
