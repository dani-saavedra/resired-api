package com.resired.api.guard.domain.exception;

public class InvalidRolException extends RuntimeException {
    public InvalidRolException(String user) {
        super("the user " + user + " does not have the correct role");
    }
}
