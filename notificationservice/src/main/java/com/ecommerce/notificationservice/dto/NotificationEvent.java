package com.ecommerce.notificationservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String recipient;
    private String message;
    private String type;      // EMAIL, SMS, PUSH
    private String priority;  // HIGH, MEDIUM, LOW
    private String scheduledTime;
}