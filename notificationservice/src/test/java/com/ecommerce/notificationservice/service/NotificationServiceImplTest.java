package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationRequest;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.exception.ResourceNotFoundException;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper to create NotificationRequest
    private NotificationRequest createRequest() {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("user@example.com");
        request.setMessage("Test message");
        request.setNotificationType(Notification.NotificationType.EMAIL);
        request.setNotificationPriority(Notification.NotificationPriority.HIGH);
        request.setScheduledTime("2025-07-25");
        return request;
    }

    @Test
    void testSendNotification_success() {
        NotificationRequest request = createRequest();

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);

        // Mock save to return an object with ID
        when(notificationRepository.save(any())).thenAnswer(invocation -> {
            Notification n = invocation.getArgument(0);
            n.setNotificationId(1L);
            return n;
        });

        Notification result = notificationService.sendNotification(request);

        verify(notificationRepository, times(1)).save(captor.capture());

        Notification savedNotification = captor.getValue();
        assertEquals("user@example.com", savedNotification.getRecipient());
        assertEquals("Test message", savedNotification.getMessage());
        assertEquals(Notification.NotificationType.EMAIL, savedNotification.getNotificationType());
        assertEquals(LocalDate.of(2025,7,25).atStartOfDay(), savedNotification.getScheduledTime());
        assertEquals(Notification.NotificationStatus.PENDING, savedNotification.getNotificationStatus());

        assertNotNull(result);
        assertEquals(1L, result.getNotificationId());
    }


    @Test
    void testGetDeliveryStatus_found() {
        Notification notification = Notification.builder()
                .notificationId(5L)
                .recipient("user2@example.com")
                .notificationStatus(Notification.NotificationStatus.SENT)
                .build();

        when(notificationRepository.findById(5)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getDeliveryStatus(5);

        assertNotNull(result);
        assertEquals(5L, result.getNotificationId());
        assertEquals(Notification.NotificationStatus.SENT, result.getNotificationStatus());

        verify(notificationRepository, times(1)).findById(5);
    }

    @Test
    void testGetDeliveryStatus_notFound() {
        when(notificationRepository.findById(10)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            notificationService.getDeliveryStatus(10);
        });

        assertTrue(exception.getMessage().contains("Notification not found with id: 10"));
        verify(notificationRepository, times(1)).findById(10);
    }

    @Test
    void testGetNotificationByRecipent() {
        List<Notification> list = List.of(
                Notification.builder().recipient("test@example.com").build(),
                Notification.builder().recipient("test@example.com").build()
        );
        when(notificationRepository.findByRecipient("test@example.com")).thenReturn(list);

        List<Notification> result = notificationService.getNotificationByRecipent("test@example.com");

        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findByRecipient("test@example.com");
    }

    @Test
    void testGetNotificationByStatus() {
        List<Notification> list = List.of(
                Notification.builder().notificationStatus(Notification.NotificationStatus.PENDING).build(),
                Notification.builder().notificationStatus(Notification.NotificationStatus.PENDING).build()
        );
        when(notificationRepository.findByNotificationStatus(Notification.NotificationStatus.PENDING)).thenReturn(list);

        List<Notification> result = notificationService.getNotificationByStatus(Notification.NotificationStatus.PENDING);

        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findByNotificationStatus(Notification.NotificationStatus.PENDING);
    }

    @Test
    void testGetNotificationByDateRange() {
        LocalDate from = LocalDate.of(2025, 7, 20);
        LocalDate to = LocalDate.of(2025, 7, 25);

        List<Notification> list = Arrays.asList(
                Notification.builder().notificationId(1L).build(),
                Notification.builder().notificationId(2L).build()
        );

        when(notificationRepository.findByCreatedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(list);

        List<Notification> result = notificationService.getNotificationByDateRange(from, to);

        assertEquals(2, result.size());
        verify(notificationRepository).findByCreatedAtBetween(
                from.atStartOfDay(),
                to.plusDays(1).atStartOfDay()
        );
    }

    @Test
    void testSendNotifications() {
        LocalDateTime now = LocalDateTime.now();

        List<Notification> pendingList = new ArrayList<>();
        Notification n1 = Notification.builder()
                .notificationId(1L)
                .notificationStatus(Notification.NotificationStatus.PENDING)
                .scheduledTime(now.minusMinutes(1))
                .build();
        Notification n2 = Notification.builder()
                .notificationId(2L)
                .notificationStatus(Notification.NotificationStatus.PENDING)
                .scheduledTime(now.minusHours(1))
                .build();
        pendingList.add(n1);
        pendingList.add(n2);

        // Use 'any(LocalDateTime.class)' matcher so Mockito does not require exact match
        when(notificationRepository.findByNotificationStatusAndScheduledTimeLessThanEqual(
                eq(Notification.NotificationStatus.PENDING),
                any(LocalDateTime.class)))
                .thenReturn(pendingList);

        when(notificationRepository.save(any(Notification.class))).thenAnswer(i -> i.getArgument(0));

        List<Notification> result = notificationService.sendNotifications();

        // Verify using any(LocalDateTime.class) for second param
        verify(notificationRepository, times(1))
                .findByNotificationStatusAndScheduledTimeLessThanEqual(eq(Notification.NotificationStatus.PENDING), any(LocalDateTime.class));

        verify(notificationRepository, times(2)).save(any(Notification.class));

        // Verify statuses updated
        for (Notification n : result) {
            assertEquals(Notification.NotificationStatus.SENT, n.getNotificationStatus());
        }
    }
}
