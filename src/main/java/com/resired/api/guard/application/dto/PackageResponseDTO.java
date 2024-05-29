package com.resired.api.guard.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.PackageStatusEnum;

import java.time.LocalDateTime;

public record PackageResponseDTO(
    @JsonProperty("guard_id") Integer guardId,
    String block,
    @JsonProperty("home_number") String homeNumber,
    String receiver,
    @JsonProperty("tracking_number") String trackingNumber,
    @JsonProperty("package_transporter") String packageTransporter,
    String description,
    PackageStatusEnum status,
    @JsonProperty("created_date") LocalDateTime createdDate) {
}
