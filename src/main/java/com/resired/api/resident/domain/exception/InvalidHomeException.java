package com.resired.api.resident.domain.exception;

public class InvalidHomeException extends RuntimeException {
    public InvalidHomeException() {
        super("Home is not valid");
    }
}
