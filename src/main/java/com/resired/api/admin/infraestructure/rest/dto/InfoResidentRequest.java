package com.resired.api.admin.infraestructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InfoResidentRequest(@JsonProperty(value = "document_id", required = true) String documentId,
                                  @JsonProperty(value = "document_type", required = true) String documentType,
                                  @JsonProperty(value = "first_name", required = true) String firstName,
                                  @JsonProperty(value = "last_name", required = true) String lastName,
                                  @JsonProperty(value = "home_id", required = true) Integer homeId,
                                  String email) {
}
