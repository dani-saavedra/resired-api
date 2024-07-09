package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationResponseDto(Integer id, String title, String message, String date,
                                      @JsonProperty("category_name") String categoryName,
                                      String priority) {
}
