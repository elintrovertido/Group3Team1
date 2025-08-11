package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationResponse;
import com.example.notificationservice.dto.SendNotificationRequest;
import com.example.notificationservice.entity.NotificationEntity;
import com.example.notificationservice.entity.NotificationType;
import com.example.notificationservice.entity.Status;
import com.example.notificationservice.repository.NotificationRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class NotificationService {

    private final NotificationRepository repository;
    private final EmailSender emailSender;
    private final ModelMapper modelMapper = new ModelMapper();
    private final Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationResponse sendNotification(SendNotificationRequest request) {
        Set<jakarta.validation.ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        NotificationEntity entity = modelMapper.map(request, NotificationEntity.class);
        entity.setStatus(Status.PENDING);
        repository.save(entity);

        if (entity.getScheduledTime() == null || entity.getScheduledTime().isBefore(LocalDateTime.now())) {
            sendNow(entity);
        }

        return modelMapper.map(entity, NotificationResponse.class);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendNow(NotificationEntity entity) {
        try {
            if (entity.getType() == NotificationType.EMAIL) {
                emailSender.send(entity);
            } else {
                throw new UnsupportedOperationException("Only EMAIL is supported");
            }
            entity.setStatus(Status.SENT);
        } catch (Exception e) {
            entity.setStatus(Status.FAILED);
            logger.error("Failed to send notification {}: {}", entity.getId(), e.getMessage());
            throw new RuntimeException("Send failed", e);
        } finally {
            repository.save(entity);
        }
    }

    public NotificationResponse getStatus(Long id) {
        return repository.findById(id)
                .map(e -> modelMapper.map(e, NotificationResponse.class))
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public List<NotificationResponse> listNotifications(String recipient, LocalDateTime start, LocalDateTime end, Status status) {
        List<NotificationEntity> entities = repository.findByRecipientAndCreatedAtBetweenAndStatus(recipient, start, end, status);
        return entities.stream().map(e -> modelMapper.map(e, NotificationResponse.class)).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 60000)  // Every minute
    public void processScheduled() {
        List<NotificationEntity> pending = repository.findByStatus(Status.PENDING).stream()
                .filter(n -> n.getScheduledTime() != null && n.getScheduledTime().isBefore(LocalDateTime.now()))
                .toList();
        pending.forEach(this::sendNow);
    }
} 
