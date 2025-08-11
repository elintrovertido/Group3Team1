package com.example.notificationservice.service;

import com.example.notificationservice.entity.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender implements NotificationSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final JavaMailSender mailSender;

    @Value("${app.notification.from.email}")
    private String fromEmail;

    @Override
    public void send(NotificationEntity notification) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(notification.getRecipient());
            message.setSubject("Notification from Notification Service");
            message.setText(notification.getMessageContent());
            mailSender.send(message);
            logger.info("Email sent to {}", notification.getRecipient());
        } catch (MailException e) {
            logger.error("Failed to send email to {}: {}", notification.getRecipient(), e.getMessage());
            throw new RuntimeException("Email send failed", e);
        }
    }
} 
