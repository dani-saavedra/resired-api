package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

import java.util.List;

public record CreateNotificationForBlocksDto(String title, String message,
                                             @JsonProperty("category_id") Integer categoryId,
                                             LevelNotificationEnum priority,
                                             @JsonProperty("blocks_id") List<Integer> blocksId) {
}
