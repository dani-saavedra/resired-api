package com.resired.api.resident.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VisitorRequestDTO(String name, @JsonProperty("document_id") String documentId,
                                String telephone, @JsonProperty("home_id") Integer homeId) {

}
