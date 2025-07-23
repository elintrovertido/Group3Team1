package com.ecommerce.notificationservice.controller;


import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<List<Notification>> getNotificationByRecipent(@RequestParam String recipent){
        List<Notification> notificationList = notificationService.getNotificationByRecipent(recipent);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Notification>> getNotificationByStatus(@RequestParam String status){
        List<Notification> notificationList = notificationService.getNotificationByStatus(status);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Notification>> getNotificationByDateRange(@RequestParam String fromDate, @RequestParam String toDate){
        List<Notification> notificationList = notificationService.getNotificationByDateRange(fromDate, toDate);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

}
