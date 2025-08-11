package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationResponse;
import com.example.notificationservice.dto.SendNotificationRequest;
import com.example.notificationservice.entity.Status;
import com.example.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody SendNotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sendNotification(request));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<NotificationResponse> getStatus(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStatus(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> list(
            @RequestParam(required = false) String recipient,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            @RequestParam(required = false) Status status) {
        return ResponseEntity.ok(service.listNotifications(recipient, start, end, status));
    }
} 
