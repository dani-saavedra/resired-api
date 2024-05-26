package com.resired.api.security.infraestructure.rest.proxy;

public enum ErrorCode {
    USER01("User inactive"),
    USER02("Invalid credentials"),
    USER03("Invalid role"),
    VISIT01("QR invalid"),
    GENERAL("Unknown error");

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
