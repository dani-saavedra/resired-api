package com.resired.api.admin.infraestructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.DocumentTypeEnum;

public record InfoUserRequest(@JsonProperty(value = "document_id", required = true) String documentId,
                              @JsonProperty(value = "document_type", required = true) DocumentTypeEnum documentType,
                              @JsonProperty(value = "first_name", required = true) String firstName,
                              @JsonProperty(value = "last_name", required = true) String lastName,
                              @JsonProperty(value = "home_id") Integer homeId,
                              String email) {
}
