package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResidentRequestDto(@JsonProperty("first_name") String firstName,
                                 @JsonProperty("last_name") String lastName, String email, String document,
                                 String house, Integer neighborhood, Integer id) {
}
