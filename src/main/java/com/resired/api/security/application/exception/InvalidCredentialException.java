package com.resired.api.security.application.exception;

public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException(String email) {
        super("Invalid credentials " + email);
    }
}
