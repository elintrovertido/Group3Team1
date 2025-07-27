package com.ecommerce.notificationservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime scheduled = LocalDateTime.of(2025, 7, 25, 14, 30);

        Notification notification = Notification.builder()
                .notificationId(100L)
                .recipient("test@example.com")
                .message("Test notification message")
                .notificationType(Notification.NotificationType.EMAIL)
                .notificationPriority(Notification.NotificationPriority.HIGH)
                .notificationStatus(Notification.NotificationStatus.PENDING)
                .scheduledTime(scheduled)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(100L, notification.getNotificationId());
        assertEquals("test@example.com", notification.getRecipient());
        assertEquals("Test notification message", notification.getMessage());
        assertEquals(Notification.NotificationType.EMAIL, notification.getNotificationType());
        assertEquals(Notification.NotificationPriority.HIGH, notification.getNotificationPriority());
        assertEquals(Notification.NotificationStatus.PENDING, notification.getNotificationStatus());
        assertEquals(scheduled, notification.getScheduledTime());
        assertNotNull(notification.getCreatedAt());
        assertNotNull(notification.getUpdatedAt());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Notification notification = new Notification();

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = createdAt.plusMinutes(1);
        LocalDateTime scheduled = LocalDateTime.of(2025, 7, 26, 10, 0);

        notification.setNotificationId(101L);
        notification.setRecipient("user2@example.com");
        notification.setMessage("Second test message");
        notification.setNotificationType(Notification.NotificationType.SMS);
        notification.setNotificationPriority(Notification.NotificationPriority.MEDIUM);
        notification.setNotificationStatus(Notification.NotificationStatus.SENT);
        notification.setScheduledTime(scheduled);
        notification.setCreatedAt(createdAt);
        notification.setUpdatedAt(updatedAt);

        assertEquals(101L, notification.getNotificationId());
        assertEquals("user2@example.com", notification.getRecipient());
        assertEquals("Second test message", notification.getMessage());
        assertEquals(Notification.NotificationType.SMS, notification.getNotificationType());
        assertEquals(Notification.NotificationPriority.MEDIUM, notification.getNotificationPriority());
        assertEquals(Notification.NotificationStatus.SENT, notification.getNotificationStatus());
        assertEquals(scheduled, notification.getScheduledTime());
        assertEquals(createdAt, notification.getCreatedAt());
        assertEquals(updatedAt, notification.getUpdatedAt());
    }

    @Test
    void testEnumValues() {
        assertEquals("EMAIL", Notification.NotificationType.EMAIL.name());
        assertEquals("SMS", Notification.NotificationType.SMS.name());
        assertEquals("PUSH", Notification.NotificationType.PUSH.name());

        assertEquals("PENDING", Notification.NotificationStatus.PENDING.name());
        assertEquals("SENT", Notification.NotificationStatus.SENT.name());
        assertEquals("FAILED", Notification.NotificationStatus.FAILED.name());
        assertEquals("RETRIED", Notification.NotificationStatus.RETRIED.name());

        assertEquals("HIGH", Notification.NotificationPriority.HIGH.name());
        assertEquals("MEDIUM", Notification.NotificationPriority.MEDIUM.name());
        assertEquals("LOW", Notification.NotificationPriority.LOW.name());
    }
}
