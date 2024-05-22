package com.resired.api.guard.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Visitor(@JsonProperty("visitor_name") String visitorName, @JsonProperty("visitor_document") String visitorDocument,
                      String home, String authorizer,boolean availableToEnter) {
}
