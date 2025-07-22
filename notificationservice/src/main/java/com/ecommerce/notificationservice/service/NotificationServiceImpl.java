package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

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

}
