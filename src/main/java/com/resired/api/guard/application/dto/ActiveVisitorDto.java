package com.resired.api.guard.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ActiveVisitorDto(Integer id,
                               @JsonProperty(value = "visitor_name") String name,
                               @JsonProperty(value = "visitor_document") String document,
                               String destination) {
}
