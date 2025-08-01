package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationEvent;
import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.entity.Notification;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationConsumerTest {

    private NotificationService notificationService;
    private NotificationConsumer consumer;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        consumer = new NotificationConsumer(notificationService);
    }

    @Test
    void testListenShouldSendNotification() {

        NotificationEvent event = new NotificationEvent();
        event.setRecipient("test@example.com");
        event.setMessage("Test message");
        event.setType("EMAIL");
        event.setPriority("HIGH");
        event.setScheduledTime("2023-10-01T10:00:00Z");


        ConsumerRecord<String, NotificationEvent> record =
                new ConsumerRecord<>("order-events", 0, 0L, null, event);


        consumer.listen(record);


        ArgumentCaptor<NotificationRequest> captor = ArgumentCaptor.forClass(NotificationRequest.class);
        verify(notificationService, times(1)).sendNotification(captor.capture());

        NotificationRequest sentReq = captor.getValue();
        assertEquals(event.getRecipient(), sentReq.getRecipient());
        assertEquals(event.getMessage(), sentReq.getMessage());
        assertEquals(Notification.NotificationType.valueOf(event.getType()), sentReq.getNotificationType());
        assertEquals(Notification.NotificationPriority.valueOf(event.getPriority()), sentReq.getNotificationPriority());
        assertEquals(event.getScheduledTime(), sentReq.getScheduledTime());
    }
}
