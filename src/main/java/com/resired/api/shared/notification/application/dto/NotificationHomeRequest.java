package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record NotificationHomeRequest(String title, String message, @JsonProperty("home_id") Integer homeID,
                                      @JsonProperty("category_id") Integer categoryId,
                                      LevelNotificationEnum level) {
}
