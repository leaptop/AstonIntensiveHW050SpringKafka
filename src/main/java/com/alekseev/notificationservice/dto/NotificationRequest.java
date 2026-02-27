package com.alekseev.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
        @NotBlank String operation,
        @Email @NotBlank String email
) {}