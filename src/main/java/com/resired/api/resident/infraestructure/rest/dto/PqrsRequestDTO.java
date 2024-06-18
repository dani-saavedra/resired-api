package com.resired.api.resident.infraestructure.rest.dto;

import com.resired.api.resident.domain.enums.CategoryPQRS;

public record PqrsRequestDTO(String title, CategoryPQRS category, String description) {

}
