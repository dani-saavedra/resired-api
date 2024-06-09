package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuardResponseDto {

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("guard_name")
    private String guardName;

    @JsonProperty("email")
    private String email;
}
