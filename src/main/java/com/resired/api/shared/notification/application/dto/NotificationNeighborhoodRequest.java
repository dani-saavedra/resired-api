package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record NotificationNeighborhoodRequest(String title, String message,
                                              @JsonProperty("neighborhood_id") Integer neighborhoodID,
                                              @JsonProperty("category_id") Integer categoryId,
                                              LevelNotificationEnum level) {
}
