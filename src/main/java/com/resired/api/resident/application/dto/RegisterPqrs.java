package com.resired.api.resident.application.dto;

import com.resired.api.resident.domain.enums.CategoryPQRS;

public record RegisterPqrs(Integer neighbor, Integer residentId, String title, CategoryPQRS category,
                           String description) {

}
