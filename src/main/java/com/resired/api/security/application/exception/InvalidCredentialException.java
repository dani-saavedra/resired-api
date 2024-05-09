package com.resired.api.security.application.exception;

public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException() {
        super("Invalid credentials");
    }
}
