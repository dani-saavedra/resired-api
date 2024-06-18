package com.resired.api.resident.application.dto;

import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.domain.enums.StatePQRS;

public record PqrsResponseDTO(String createdAt, String title, CategoryPQRS category, String ticket,
                              StatePQRS state) {
}
