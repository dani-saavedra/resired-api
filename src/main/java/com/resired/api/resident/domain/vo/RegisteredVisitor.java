package com.resired.api.resident.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisteredVisitor(Integer id, String name, String document, boolean favorite,
                                @JsonProperty(value = "allow_to_enter") boolean allowToEnter) {
}
