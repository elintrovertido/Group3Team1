package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationEvent;
import com.ecommerce.notificationservice.service.KafkaNotificationProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;


class KafkaNotificationProducerTest {

    private KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    private KafkaNotificationProducer producer;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        producer = new KafkaNotificationProducer(kafkaTemplate);
    }

    @Test
    void testSendToOrderTopic() {
        NotificationEvent event = new NotificationEvent(/* set properties if needed */);

        producer.sendToOrderTopic(event);

        verify(kafkaTemplate, times(1)).send("order-events", event);
    }

    @Test
    void testSendToPaymentTopic() {
        NotificationEvent event = new NotificationEvent(/* set properties if needed */);

        producer.sendToPaymentTopic(event);

        verify(kafkaTemplate, times(1)).send("payment-events", event);
    }
}
