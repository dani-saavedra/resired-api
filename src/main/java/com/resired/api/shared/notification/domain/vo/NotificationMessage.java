package com.resired.api.shared.notification.domain.vo;

public record NotificationMessage(Integer id, String title, String message, Boolean viewed, String date) {
}
