package com.resired.api.security.application.exception;

public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super("The Token has expired");
    }
}
