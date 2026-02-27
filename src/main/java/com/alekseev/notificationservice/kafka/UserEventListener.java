package com.alekseev.notificationservice.kafka;

import com.alekseev.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserEventListener {
    private final NotificationService notificationService;

    public UserEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleUserEvent(Map<String, Object> message) {
        String operation = (String) message.get("operation");
        String email = (String) message.get("email");
        notificationService.sendNotification(operation, email);
    }
}