package com.resired.api.admin.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResidentHome(@JsonProperty("document_id") String documentId,
                           @JsonProperty("document_type") String documentType,
                           @JsonProperty("last_name") String lastName,
                           @JsonProperty("first_name") String firstName,
                           @JsonProperty("email") String email,
                           Integer id) {

}
