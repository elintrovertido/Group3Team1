package com.ecommerce.notificationservice.dto;

import com.ecommerce.notificationservice.entity.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRequestTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        NotificationRequest request = NotificationRequest.builder()
                .recipient("user@example.com")
                .message("Sample message")
                .notificationType(Notification.NotificationType.EMAIL)
                .notificationPriority(Notification.NotificationPriority.HIGH)
                .scheduledTime("2025-07-25T10:15:30")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals("user@example.com", request.getRecipient());
        assertEquals("Sample message", request.getMessage());
        assertEquals(Notification.NotificationType.EMAIL, request.getNotificationType());
        assertEquals(Notification.NotificationPriority.HIGH, request.getNotificationPriority());
        assertEquals("2025-07-25T10:15:30", request.getScheduledTime());
        assertEquals(now, request.getCreatedAt());
        assertEquals(now, request.getUpdatedAt());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        NotificationRequest request = new NotificationRequest();

        request.setRecipient("user2@example.com");
        request.setMessage("Another message");
        request.setNotificationType(Notification.NotificationType.SMS);
        request.setNotificationPriority(Notification.NotificationPriority.MEDIUM);
        request.setScheduledTime("2025-08-01T09:00:00");

        LocalDateTime createdAt = LocalDateTime.of(2025, 8, 1, 9, 0);
        request.setCreatedAt(createdAt);
        request.setUpdatedAt(createdAt);

        assertEquals("user2@example.com", request.getRecipient());
        assertEquals("Another message", request.getMessage());
        assertEquals(Notification.NotificationType.SMS, request.getNotificationType());
        assertEquals(Notification.NotificationPriority.MEDIUM, request.getNotificationPriority());
        assertEquals("2025-08-01T09:00:00", request.getScheduledTime());
        assertEquals(createdAt, request.getCreatedAt());
        assertEquals(createdAt, request.getUpdatedAt());
    }

    @Test
    void testEnums() {
        assertEquals("EMAIL", NotificationRequest.NotificationType.EMAIL.name());
        assertEquals("PENDING", NotificationRequest.NotificationStatus.PENDING.name());
        assertEquals("HIGH", NotificationRequest.NotificationPriority.HIGH.name());
    }
}
