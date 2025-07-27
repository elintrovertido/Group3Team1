package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.NotificationEvent;
import com.ecommerce.notificationservice.service.KafkaNotificationProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class KafkaNotificationTestController {

    private final KafkaNotificationProducer producer;

    public KafkaNotificationTestController(KafkaNotificationProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send/order")
    public ResponseEntity<String> sendOrderNotification(@RequestBody NotificationEvent event) {
        producer.sendToOrderTopic(event);
        return ResponseEntity.ok("Notification sent to order-events");
    }

    @PostMapping("/send/payment")
    public ResponseEntity<String> sendPaymentNotification(@RequestBody NotificationEvent event) {
        producer.sendToPaymentTopic(event);
        return ResponseEntity.ok("Notification sent to payment-events");
    }
}
