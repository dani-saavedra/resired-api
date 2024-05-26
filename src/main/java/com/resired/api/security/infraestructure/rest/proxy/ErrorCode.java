package com.resired.api.security.infraestructure.rest.proxy;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER01("User inactive"),
    USER02("Invalid credentials"),
    USER03("Invalid role"),
    VISIT01("QR invalid"),
    VISIT02("Visitor is invalid"),
    GENERAL("Unknown error"),
    GENERAL_RESOURCE("Invalid url");

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

}
