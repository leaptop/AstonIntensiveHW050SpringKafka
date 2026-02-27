package com.alekseev.notificationservice.controller;
import com.alekseev.notificationservice.dto.NotificationRequest;
import com.alekseev.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody NotificationRequest request) {
        notificationService.sendNotification(request.operation(), request.email());
        return ResponseEntity.ok().build();
    }
}