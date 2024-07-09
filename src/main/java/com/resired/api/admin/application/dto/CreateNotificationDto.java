package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record CreateNotificationDto(String title, String message,
                                    @JsonProperty("category_id") Integer categoryId,
                                    LevelNotificationEnum priority) {
}
