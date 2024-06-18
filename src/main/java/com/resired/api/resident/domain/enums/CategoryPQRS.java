package com.resired.api.resident.domain.enums;

import lombok.Getter;

public enum CategoryPQRS {

    PETICION("P"), QUEJA("Q"), RECLAMO("R"), SUGERENCIA("S");

    @Getter
    private final String code;

    CategoryPQRS(String code) {
        this.code = code;
    }
}
