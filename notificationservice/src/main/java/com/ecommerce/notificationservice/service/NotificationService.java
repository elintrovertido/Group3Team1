package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(Notification notification);

    NotificationResponse getDeliveryStatus(int notificationId);

    List<Notification> getNotificationByRecipent(String recipent);

    List<Notification> getNotificationByStatus(String status);

    List<Notification> getNotificationByDateRange(String fromDate, String toDate);
}
