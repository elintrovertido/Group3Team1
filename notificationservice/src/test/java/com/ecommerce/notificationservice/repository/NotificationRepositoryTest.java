package com.ecommerce.notificationservice.repository;

import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.entity.Notification.NotificationStatus;
import com.ecommerce.notificationservice.entity.Notification.NotificationPriority;
import com.ecommerce.notificationservice.entity.Notification.NotificationType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    private Notification notification1;
    private Notification notification2;
    private Notification notification3;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();

        notification1 = Notification.builder()
                .recipient("user1@example.com")
                .message("Message 1")
                .notificationType(NotificationType.EMAIL)
                .notificationPriority(NotificationPriority.HIGH)
                .notificationStatus(NotificationStatus.PENDING)
                .scheduledTime(LocalDateTime.now().minusMinutes(10))
                .build();

        notification2 = Notification.builder()
                .recipient("user2@example.com")
                .message("Message 2")
                .notificationType(NotificationType.SMS)
                .notificationPriority(NotificationPriority.MEDIUM)
                .notificationStatus(NotificationStatus.SENT)
                .scheduledTime(LocalDateTime.now().minusHours(1))
                .build();

        notification3 = Notification.builder()
                .recipient("user1@example.com")
                .message("Message 3")
                .notificationType(NotificationType.PUSH)
                .notificationPriority(NotificationPriority.LOW)
                .notificationStatus(NotificationStatus.PENDING)
                .scheduledTime(LocalDateTime.now().plusMinutes(30))
                .build();

        notificationRepository.saveAll(List.of(notification1, notification2, notification3));
    }

    @Test
    void testFindByRecipient() {
        List<Notification> result = notificationRepository.findByRecipient("user1@example.com");
        assertThat(result).hasSize(2);
        assertThat(result).extracting("recipient").containsOnly("user1@example.com");
    }

    @Test
    void testFindByNotificationStatus() {
        List<Notification> pendingNotifications = notificationRepository.findByNotificationStatus(NotificationStatus.PENDING);
        assertThat(pendingNotifications).hasSize(2);
        assertThat(pendingNotifications).extracting("notificationStatus").containsOnly(NotificationStatus.PENDING);
    }

    @Test
    void testFindByCreatedAtBetween() {
        LocalDateTime from = LocalDateTime.now().minusHours(2);
        LocalDateTime to = LocalDateTime.now();


        List<Notification> notifications = notificationRepository.findByCreatedAtBetween(from, to);

        assertThat(notifications).hasSizeGreaterThanOrEqualTo(3);
    }

    @Test
    void testFindByNotificationStatusAndScheduledTimeLessThanEqual() {
        LocalDateTime now = LocalDateTime.now();

        List<Notification> notifications = notificationRepository
                .findByNotificationStatusAndScheduledTimeLessThanEqual(NotificationStatus.PENDING, now);


        assertThat(notifications).hasSize(1);
        assertThat(notifications.get(0).getRecipient()).isEqualTo("user1@example.com");
        assertThat(notifications.get(0).getScheduledTime()).isBeforeOrEqualTo(now);
        assertThat(notifications.get(0).getNotificationStatus()).isEqualTo(NotificationStatus.PENDING);
    }
}
