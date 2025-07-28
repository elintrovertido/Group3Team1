package com.ecommerce.notificationservice.config;


import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
public class ScheduleNotification {
    private final NotificationService notificationService;

    public ScheduleNotification(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Runs every 5 minutes
    @Scheduled(cron = "0 */5 * * * *")
    public void runNotificationScheduler() {
        log.info("Running scheduler at " + LocalDateTime.now());

        List<Notification> sentNotifications = notificationService.sendNotifications();

        log.info("Notifications sent: " + sentNotifications.size());
    }


}
