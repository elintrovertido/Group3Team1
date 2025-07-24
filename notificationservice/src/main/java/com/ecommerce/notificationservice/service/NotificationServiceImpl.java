package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.exception.ResourceNotFoundException;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification sendNotification(NotificationRequest notificationRequest) {
        LocalDate date = LocalDate.parse(notificationRequest.getScheduledTime());
        LocalDateTime scheduledTime = date.atStartOfDay();
        log.info("sendNotification called with request: {}", notificationRequest);
        Notification savedNotification =  Notification.builder()
                .recipient(notificationRequest.getRecipient())
                .message(notificationRequest.getMessage())
                .notificationType(notificationRequest.getNotificationType())
                .notificationPriority(notificationRequest.getNotificationPriority())
                .notificationStatus(Notification.NotificationStatus.PENDING)
                .scheduledTime(scheduledTime)
                .build();

        notificationRepository.save(savedNotification);
        log.info("Notification saved with ID: {}", savedNotification.getNotificationId());
        return savedNotification;
    }

    @Override
    public Notification getDeliveryStatus(int notificationId) {
        log.info("getDeliveryStatus called with notificationId: {}", notificationId);
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if(optionalNotification.isPresent()){
            Notification notification = optionalNotification.get();
            log.info("Notification found: ID {}, status {}", notificationId, notification.getNotificationStatus());
            NotificationResponse notificationResponse = NotificationResponse.builder()
                    .notificationId(notification.getNotificationId())
                    .notificationStatus(notification.getNotificationStatus())
                    .build();
            return notification;
        }else{
            log.error("Notification not found with id: {}", notificationId);
            throw new ResourceNotFoundException("Notification not found with id: " + notificationId);
        }
    }

    @Override
    public List<Notification> getNotificationByRecipent(String recipient) {
        log.info("getNotificationByRecipent called for recipient: {}", recipient);
        List<Notification> notificationList = notificationRepository.findByRecipient(recipient);
        log.info("Found {} notifications for recipient {}", notificationList.size(), recipient);
        return notificationList;
    }

    @Override
    public List<Notification> getNotificationByStatus(Notification.NotificationStatus status) {
        log.info("getNotificationByStatus called with status: {}", status);
        List<Notification> notificationList = notificationRepository.findByNotificationStatus(status);
        log.info("Found {} notifications with status {}", notificationList.size(), status);
        return notificationList;
    }

    @Override
    public List<Notification> getNotificationByDateRange(LocalDate fromDate, LocalDate toDate) {
        log.info("getNotificationByDateRange called from {} to {}", fromDate, toDate);
        LocalDateTime fromDateTime = fromDate.atStartOfDay(); // 00:00:00
        LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();
        List<Notification> notificationList = notificationRepository.findByCreatedAtBetween(fromDateTime, toDateTime);
        log.info("Found {} notifications between {} and {}", notificationList.size(), fromDateTime, toDateTime);
        return notificationList;
    }

    @Override
    public List<Notification> sendNotifications() {
        log.info("sendNotifications scheduled run started");
        LocalDateTime now = LocalDateTime.now();
        List<Notification> toSend = notificationRepository.findByNotificationStatusAndScheduledTimeLessThanEqual(Notification.NotificationStatus.PENDING, now);
        log.info("Found {} pending notifications to send at {}", toSend.size(), now);
        for(Notification notification : toSend){
            notification.setNotificationStatus(Notification.NotificationStatus.SENT);
            notificationRepository.save(notification);
        }
        log.info("sendNotifications scheduled run completed");
        return toSend;
    }

}
