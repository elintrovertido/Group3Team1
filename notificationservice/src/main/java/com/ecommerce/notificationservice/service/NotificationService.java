package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;

import java.time.LocalDate;
import java.util.List;

public interface NotificationService {
    Notification sendNotification(NotificationRequest notification);

    Notification getDeliveryStatus(int notificationId);

    List<Notification> getNotificationByRecipent(String recipent);

    List<Notification> getNotificationByStatus(Notification.NotificationStatus status);

    List<Notification> getNotificationByDateRange(LocalDate fromDate, LocalDate toDate);

    List<Notification> sendNotifications();
}
