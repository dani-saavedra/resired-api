package com.resired.api.shared.notification.application.dto;

public record NotificationForUserDto(Integer id, String title, String message, String date, Boolean viewed) {
}
