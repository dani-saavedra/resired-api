package com.resired.api.guard.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PackageRequestDTO(@JsonProperty("receiver_name") String receiver, @JsonProperty("tracking_number") String trackingNumber,
                                @JsonProperty("package_transporter") String packageTransporter, String description,
                                String block, @JsonProperty("home_number") String homeNumber) {
}
