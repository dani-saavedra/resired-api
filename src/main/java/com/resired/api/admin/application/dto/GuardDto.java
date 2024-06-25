package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GuardDto(@JsonProperty("document_id") String documentId,
                       @JsonProperty("document_type") String documentType,
                       @JsonProperty("guard_last_name") String guardLastName,
                       @JsonProperty("guard_first_name") String guardFirstName,
                       @JsonProperty("email") String email,
                       Integer id) {
}
