package com.example.notificationservice.dto;

import com.example.notificationservice.entity.NotificationType;
import com.example.notificationservice.entity.Priority;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendNotificationRequest {

    @NotBlank(message = "Recipient is required")
    @Email(message = "Invalid email format")
    private String recipient;

    @NotBlank(message = "Message content is required")
    @Size(max = 1000, message = "Message content must not exceed 1000 characters")
    private String messageContent;

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @FutureOrPresent(message = "Scheduled time cannot be in the past")
    private LocalDateTime scheduledTime;
} 
