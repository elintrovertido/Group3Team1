package com.ecommerce.notificationservice.service;


import com.ecommerce.notificationservice.dto.NotificationEvent;
import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @KafkaListener(groupId = "notification-group", topics = {"order-events", "payment-events"})
    public void listen(ConsumerRecord<String, NotificationEvent> record){
        NotificationEvent notificationreq = record.value();
        NotificationRequest notification = NotificationRequest.builder()
                .recipient(notificationreq.getRecipient())
                .message(notificationreq.getMessage())
                .notificationType(Notification.NotificationType.valueOf(notificationreq.getType()))
                .notificationPriority(Notification.NotificationPriority.valueOf(notificationreq.getPriority()))
                .scheduledTime(notificationreq.getScheduledTime())
                .build();
        log.info(notification.toString());
        notificationService.sendNotification(notification);

    }
}
