package com.resired.api.resident.domain.enums;

import lombok.Getter;

@Getter
public enum DocumentTypeEnum {

    CC("Cédula de ciudadanía"), CE("Cédula de extranjería"), TI("Tarjeta de identidad"), PA("Pasaporte");

    private final String value;

    DocumentTypeEnum(String value) {
        this.value = value;
    }
}
