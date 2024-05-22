package com.resired.api.security.application.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException(String documentId) {
        super("User " + documentId + "is inactive");
    }
}
