package com.resired.api.security.domain.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException(String documentId) {
        super("User " + documentId + "is inactive");
    }
}
