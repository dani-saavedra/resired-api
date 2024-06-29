package com.resired.api.resident.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.domain.enums.StatePQRS;

public record PqrsDetailDTO(@JsonProperty("created_at") String createdAt,
                            @JsonProperty("response_at") String responseAt,
                            String title, CategoryPQRS category,
                            String ticket, StatePQRS state, @JsonProperty("admin_response") String adminResponse,
                            String response, String description, String resident) {
}
