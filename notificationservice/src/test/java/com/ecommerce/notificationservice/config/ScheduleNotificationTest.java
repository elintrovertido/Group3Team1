package com.ecommerce.notificationservice.config;

import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.mockito.Mockito.*;


class ScheduleNotificationTest {


    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ScheduleNotification scheduleNotification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRunNotificationScheduler_callsSendNotifications() {
        // Given
        Notification notification1 = Notification.builder().notificationId(1L).build();
        Notification notification2 = Notification.builder().notificationId(2L).build();
        List<Notification> mockNotifications = List.of(notification1, notification2);

        when(notificationService.sendNotifications()).thenReturn(mockNotifications);

        // When
        scheduleNotification.runNotificationScheduler();

        // Then
        verify(notificationService, times(1)).sendNotifications();
    }

}