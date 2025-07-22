package com.ecommerce.notificationservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @NotBlank(message = "Recipient cannot be blank")
    @Size(max = 255, message = "Recipient must not exceed 255 characters")
    @Column(nullable = false)
    private String recipient;

    @NotBlank(message = "Message content cannot be empty")
    @Size(max = 1000, message = "Message content must not exceed 1000 characters")
    @Column(nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    private NotificationPriority notificationPriority;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    private LocalDateTime scheduledTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
