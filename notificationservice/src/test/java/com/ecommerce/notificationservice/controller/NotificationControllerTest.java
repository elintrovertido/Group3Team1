package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.entity.Notification.NotificationStatus;
import com.ecommerce.notificationservice.service.NotificationService;
import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    public void setup() {
        notification1 = Notification.builder()
                .notificationId(1L)
                .recipient("user1@example.com")
                .message("Test message 1")
                .notificationType(null) // set your Enum value if needed
                .notificationPriority(null)
                .notificationStatus(NotificationStatus.PENDING)
                .build();

        notification2 = Notification.builder()
                .notificationId(2L)
                .recipient("user2@example.com")
                .message("Test message 2")
                .notificationType(null)
                .notificationPriority(null)
                .notificationStatus(NotificationStatus.SENT)
                .build();
    }

    @Test
    public void testSendNotification() throws Exception {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("user@example.com");
        request.setMessage("Sample message");
        request.setNotificationType(null);
        request.setNotificationPriority(null);
        request.setScheduledTime("2025-07-25");

        Notification savedNotification = Notification.builder()
                .notificationId(10L)
                .recipient("user@example.com")
                .message("Sample message")
                .notificationStatus(NotificationStatus.PENDING)
                .build();

        Mockito.when(notificationService.sendNotification(any(NotificationRequest.class))).thenReturn(savedNotification);

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notificationId").value(10))
                .andExpect(jsonPath("$.recipient").value("user@example.com"))
                .andExpect(jsonPath("$.message").value("Sample message"))
                .andExpect(jsonPath("$.notificationStatus").value("PENDING"));
    }

    @Test
    public void testCheckDeliveryStatus() throws Exception {
        Mockito.when(notificationService.getDeliveryStatus(1)).thenReturn(notification1);

        mockMvc.perform(get("/api/v1/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(1))
                .andExpect(jsonPath("$.recipient").value("user1@example.com"));
    }

    @Test
    public void testGetNotificationByRecipient() throws Exception {
        List<Notification> list = Arrays.asList(notification1, notification2);
        Mockito.when(notificationService.getNotificationByRecipent("user1@example.com")).thenReturn(list);

        mockMvc.perform(get("/api/v1/notifications/searchRecipent")
                        .param("recipent", "user1@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetNotificationByStatus() throws Exception {
        List<Notification> list = Arrays.asList(notification1);
        Mockito.when(notificationService.getNotificationByStatus(NotificationStatus.PENDING)).thenReturn(list);

        mockMvc.perform(get("/api/v1/notifications/searchStatus")
                        .param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].notificationStatus").value("PENDING"));
    }

    @Test
    public void testGetNotificationByDateRange() throws Exception {
        List<Notification> list = Arrays.asList(notification1, notification2);
        LocalDate fromDate = LocalDate.of(2025, 7, 20);
        LocalDate toDate = LocalDate.of(2025, 7, 25);

        Mockito.when(notificationService.getNotificationByDateRange(fromDate, toDate)).thenReturn(list);

        mockMvc.perform(get("/api/v1/notifications/searchDate")
                        .param("fromDate", "2025-07-20")
                        .param("toDate", "2025-07-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testSendNotifications() throws Exception {
        List<Notification> list = Arrays.asList(notification1, notification2);

        Mockito.when(notificationService.sendNotifications()).thenReturn(list);

        mockMvc.perform(get("/api/v1/notifications/sendNotifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
