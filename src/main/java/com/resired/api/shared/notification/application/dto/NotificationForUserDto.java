package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record NotificationForUserDto(Integer id, String title, String message,
                                     @JsonProperty("category_name") String categoryName,
                                     LevelNotificationEnum level, String date, Boolean viewed) {
}
