package com.ecommerce.notificationservice.dto;

import com.ecommerce.notificationservice.entity.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
@Builder
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
