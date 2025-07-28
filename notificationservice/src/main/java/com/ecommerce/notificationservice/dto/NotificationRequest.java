package com.ecommerce.notificationservice.dto;

import com.ecommerce.notificationservice.entity.Notification;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    public enum NotificationType {
        EMAIL,
        SMS,
        PUSH
    }

    public enum NotificationStatus{
        PENDING,
        SENT,
        FAILED,
        RETRIED
    }

    public enum NotificationPriority{
        HIGH,
        MEDIUM,
        LOW
    }


    private String recipient;

    private String message;

    private Notification.NotificationType notificationType;

    private Notification.NotificationPriority notificationPriority;

    private String scheduledTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
