package com.resired.api.resident.application.exception;

public class InvalidHomeException extends RuntimeException {
    public InvalidHomeException(String home) {
        super("Home is not valid: " + home);
    }
}
