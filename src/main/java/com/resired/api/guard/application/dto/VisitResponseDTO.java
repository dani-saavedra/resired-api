package com.resired.api.guard.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record VisitResponseDTO(Integer id,
                               @JsonProperty("visitor_name") String visitorName,
                               @JsonProperty("visitor_document") String visitorDocument,
                               @JsonProperty("home_number") String homeNumber,
                               @JsonProperty("check_in") String checkIn) {
}
