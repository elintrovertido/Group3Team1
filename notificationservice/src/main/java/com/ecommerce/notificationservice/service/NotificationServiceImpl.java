package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification sendNotification(Notification notificationRequest) {

        Notification notification =  Notification.builder()
                .recipient(notificationRequest.getRecipient())
                .message(notificationRequest.getMessage())
                .notificationType(notificationRequest.getNotificationType())
                .notificationPriority(notificationRequest.getNotificationPriority())
                .notificationStatus(Notification.NotificationStatus.PENDING)
                .scheduledTime(notificationRequest.getScheduledTime())
                .build();

        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public NotificationResponse getDeliveryStatus(int notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if(optionalNotification.isPresent()){
            Notification notification = optionalNotification.get();
            NotificationResponse notificationResponse = NotificationResponse.builder()
                    .notificationId(notification.getNotificationId())
                    .notificationStatus(notification.getNotificationStatus())
                    .build();
            return notificationResponse;
        }
        return new NotificationResponse();
    }

    @Override
    public List<Notification> getNotificationByRecipent(String recipent) {
        List<Notification> notificationList = notificationRepository.findByRecipient(recipent);
        return notificationList;
    }

    @Override
    public List<Notification> getNotificationByStatus(String status) {
        List<Notification> notificationList = notificationRepository.findByNotificationStatus(status);
        return notificationList;
    }

    @Override
    public List<Notification> getNotificationByDateRange(String fromDate, String toDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime fromDatex = LocalDateTime.parse(fromDate, dateTimeFormatter);
        LocalDateTime toDatex = LocalDateTime.parse(toDate, dateTimeFormatter);

        List<Notification> notificationList = notificationRepository.findByCreatedAtBetween(fromDatex, toDatex);
        return notificationList;
    }

}
