package com.resired.api.shared.notification.domain.vo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record NotificationMessage(Integer id, String title, String message, Boolean viewed, LocalDateTime createdDate) {
    public NotificationMessage(String title, String message, Boolean viewed) {
        this(0, title, message, viewed, LocalDateTime.now(ZoneOffset.UTC));
    }
}
