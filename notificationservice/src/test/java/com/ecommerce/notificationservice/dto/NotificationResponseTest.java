package com.ecommerce.notificationservice.dto;

import com.ecommerce.notificationservice.entity.Notification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Long id = 123L;
        Notification.NotificationStatus status = Notification.NotificationStatus.PENDING;

        NotificationResponse response = new NotificationResponse(id, status);

        assertEquals(id, response.getNotificationId());
        assertEquals(status, response.getNotificationStatus());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        NotificationResponse response = new NotificationResponse();

        Long id = 456L;
        Notification.NotificationStatus status = Notification.NotificationStatus.SENT;

        response.setNotificationId(id);
        response.setNotificationStatus(status);

        assertEquals(id, response.getNotificationId());
        assertEquals(status, response.getNotificationStatus());
    }

    @Test
    void testBuilder() {
        Long id = 789L;
        Notification.NotificationStatus status = Notification.NotificationStatus.FAILED;

        NotificationResponse response = NotificationResponse.builder()
                .notificationId(id)
                .notificationStatus(status)
                .build();

        assertNotNull(response);
        assertEquals(id, response.getNotificationId());
        assertEquals(status, response.getNotificationStatus());
    }
}
