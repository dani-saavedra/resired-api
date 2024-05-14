package com.resired.api.resident.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.NewsCategoryEnum;

import java.time.LocalDateTime;

public record NewsResponse(Long id, String title, String description, String image,
                           @JsonProperty("creation_date") LocalDateTime creationDate, NewsCategoryEnum category) {
}
