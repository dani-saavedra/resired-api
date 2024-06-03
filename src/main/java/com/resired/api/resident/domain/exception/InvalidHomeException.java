package com.resired.api.resident.domain.exception;

public class InvalidHomeException extends RuntimeException {
    public InvalidHomeException(Integer homeId) {
        super("Home " + homeId + " is not valid");
    }
}
