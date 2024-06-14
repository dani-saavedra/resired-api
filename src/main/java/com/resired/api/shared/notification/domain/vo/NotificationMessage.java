package com.resired.api.shared.notification.domain.vo;

public record NotificationMessage(Integer id, String title, String message, Boolean viewed, String date) {
    public NotificationMessage(String title, String message, Boolean viewed, String date) {
        this(0, title, message, viewed, date);
    }
}
