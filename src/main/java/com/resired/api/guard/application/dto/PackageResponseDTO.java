package com.resired.api.guard.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.PackageStatusEnum;

import java.time.LocalDateTime;

public record PackageResponseDTO(
    @JsonProperty("package_id") Integer packageId,
    @JsonProperty("home_number") String homeNumber,
    @JsonProperty("receiver") String receiver,
    @JsonProperty("tracking_number") String trackingNumber,
    @JsonProperty("package_transporter") String packageTransporter,
    @JsonProperty("description") String description,
    @JsonProperty("status") PackageStatusEnum status,
    @JsonProperty("created_date") LocalDateTime createdDate) {
}
