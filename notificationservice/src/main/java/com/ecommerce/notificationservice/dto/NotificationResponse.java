package com.ecommerce.notificationservice.dto;

import com.ecommerce.notificationservice.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long notificationId;
    private Notification.NotificationStatus notificationStatus;
//    private LocalDateTime scheduledTime;

}
