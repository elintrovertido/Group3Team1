package com.ecommerce.notificationservice.controller;


import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.dto.NotificationResponse;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import com.ecommerce.notificationservice.service.NotificationService;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequest notification){
        Notification sentNotification = notificationService.sendNotification(notification);
        return new ResponseEntity<>(sentNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> checkDeliveryStatus(@PathVariable("notificationId") int notificationId){
        Notification notificationResponse = notificationService.getDeliveryStatus(notificationId);
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
    }

    @GetMapping("/searchRecipent")
    public ResponseEntity<List<Notification>> getNotificationByRecipent(@RequestParam String recipent){
        List<Notification> notificationList = notificationService.getNotificationByRecipent(recipent);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @GetMapping("/searchStatus")
    public ResponseEntity<List<Notification>> getNotificationByStatus(@RequestParam String status){
        Notification.NotificationStatus notificationStatus = Notification.NotificationStatus.valueOf(status.toUpperCase());
        List<Notification> notificationList = notificationService.getNotificationByStatus(notificationStatus);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @GetMapping("/searchDate")
    public ResponseEntity<List<Notification>> getNotificationByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate){
        List<Notification> notificationList = notificationService.getNotificationByDateRange(fromDate, toDate);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }



    @GetMapping("/sendNotifications")
    public ResponseEntity<List<Notification>> sendNotifications(){
        List<Notification> notificationList = notificationService.sendNotifications();
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

}
