package com.ecommerce.notificationservice.repository;

import com.ecommerce.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
