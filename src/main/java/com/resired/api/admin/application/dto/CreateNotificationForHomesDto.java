package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

import java.util.List;

public record CreateNotificationForHomesDto(String title, String message,
                                            @JsonProperty("category_id") Integer categoryId,
                                            LevelNotificationEnum priority,
                                            @JsonProperty("homes_id") List<Integer> homesId) {
}
