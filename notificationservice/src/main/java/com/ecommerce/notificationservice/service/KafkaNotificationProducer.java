package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public KafkaNotificationProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToOrderTopic(NotificationEvent event) {
        kafkaTemplate.send("order-events", event);
    }

    public void sendToPaymentTopic(NotificationEvent event) {
        kafkaTemplate.send("payment-events", event);
    }
}

