package com.resired.api.guard.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record PackageRequestDTO(@JsonProperty("receiver_name") String receiver, @JsonProperty("tracking_number") String trackingNumber,
                                @JsonProperty("package_transporter") String packageTransporter, String description,
                                Optional<String> block, @JsonProperty("home_number") String homeNumber) {
}
