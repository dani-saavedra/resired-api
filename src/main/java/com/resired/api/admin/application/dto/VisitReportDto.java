package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VisitReportDto(@JsonProperty(value = "visitor_name") String visitorName,
                             String destination,
                             @JsonProperty(value = "visitor_document") String visitorDocument,
                             @JsonProperty(value = "check_in") String checkIn,
                             @JsonProperty(value = "guard_name") String authorizingGuardName) {
}
