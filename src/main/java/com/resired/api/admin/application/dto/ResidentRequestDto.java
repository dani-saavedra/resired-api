package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.DocumentTypeEnum;

public record ResidentRequestDto(@JsonProperty("first_name") String firstName,
                                 @JsonProperty("last_name") String lastName, String email,
                                 @JsonProperty("document_type") DocumentTypeEnum documentType, String document,
                                 String house, Integer neighborhood, Integer id) {
}
