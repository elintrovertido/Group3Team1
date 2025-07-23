package com.ecommerce.notificationservice.repository;

import com.ecommerce.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByRecipient(String recipent);

    List<Notification> findByNotificationStatus(String status);

    List<Notification> findByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
