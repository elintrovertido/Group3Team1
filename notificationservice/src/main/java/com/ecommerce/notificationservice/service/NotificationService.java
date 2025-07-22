package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;

public interface NotificationService {
    Notification sendNotification(Notification notification);

    NotificationResponse getDeliveryStatus(int notificationId);
}
