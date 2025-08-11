package com.example.notificationservice.repository;

import com.example.notificationservice.entity.NotificationEntity;
import com.example.notificationservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByRecipient(String recipient);

    List<NotificationEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<NotificationEntity> findByStatus(Status status);

    List<NotificationEntity> findByRecipientAndCreatedAtBetweenAndStatus(String recipient, LocalDateTime start, LocalDateTime end, Status status);
} 
