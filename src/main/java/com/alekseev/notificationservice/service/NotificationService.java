package com.alekseev.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(String operation, String email) {
        String subject = "Account Notification";
        String text;

        if ("create".equals(operation)) {
            text = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
        } else if ("delete".equals(operation)) {
            text = "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        log.info("📧 Preparing to send email to: {}", email);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);

            log.info("📧 Attempting to connect to SMTP server...");
            mailSender.send(message);
            log.info("✅ Email sent successfully to {}", email);

        } catch (Exception e) {
            log.error("❌ Failed to send email: {}", e.getMessage());
            log.error("Exception type: {}", e.getClass().getName());
            log.error("Stack trace:", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}