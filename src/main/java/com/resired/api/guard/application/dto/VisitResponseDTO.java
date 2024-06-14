package com.resired.api.guard.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VisitResponseDTO(Integer id,
                               @JsonProperty("visitor_name") String visitorName,
                               @JsonProperty("visitor_document") String visitorDocument,
                               @JsonProperty("destination_home") String destinationHome,
                               @JsonProperty("check_in") String checkIn) {
}
