package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record NotificationCategoryDto(Integer id, String name, @JsonProperty("default_message") String defaultMessage,
                                      LevelNotificationEnum priority,
                                      @JsonProperty("default_title") String defaultTitle) {
}
