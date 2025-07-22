package com.ecommerce.notificationservice.controller;


import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification){
        Notification sentNotification = notificationService.sendNotification(notification);
        return new ResponseEntity<>(sentNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> checkDeliveryStatus(@PathVariable int notificationId){
        NotificationResponse notificationResponse = notificationService.getDeliveryStatus(notificationId);
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<Notification> getNotificationByRecipent(@RequestParam)

}
