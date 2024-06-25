package com.resired.api.admin.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

public record Resident(@JsonProperty("document_id") String documentId,
                       @JsonProperty("document_type") String documentType,
                       @JsonProperty("last_name") String lastName,
                       @JsonProperty("first_name") String firstName,
                       @JsonProperty("email") String email,
                       @JsonProperty("house") String house,
                       Integer id) {

}
