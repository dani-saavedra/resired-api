package com.resired.api.security.application.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException(String userId) {
        super("User " + userId + "is inactive");
    }
}
