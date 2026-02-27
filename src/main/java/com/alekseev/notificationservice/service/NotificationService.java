package com.alekseev.notificationservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(String operation, String email) {
        String subject = "Account Notification";
        String text;
        if ("create".equals(operation)) {
            text = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        } else if ("delete".equals(operation)) {
            text = "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            throw new IllegalArgumentException("Invalid operation: " + operation);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}