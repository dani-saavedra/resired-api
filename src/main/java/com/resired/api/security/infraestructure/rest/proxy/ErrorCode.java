package com.resired.api.security.infraestructure.rest.proxy;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER01("User inactive"),
    USER02("Invalid credentials"),
    USER03("Invalid role"),
    USER04("Invalid Token"),
    USER05("Resident not found on home"),
    USER06("Invalid User"),
    VISIT01("QR invalid"),
    VISIT02("Visitor is invalid"),
    HOME01("Home not found"),
    GENERAL("Unknown error"),
    GENERAL_BAD_REQUEST("Bad request"),
    GENERAL_RESOURCE("Invalid url"),
    DEVICE01("Device already exists"),
    DEVICE02("Device for user not found"),
    NEIGHBORHOOD01("Number of homes exceeds that allowed"),
    PACKAGE01("Package not found"),
    PQRS01("Invalid PQRS");

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

}
