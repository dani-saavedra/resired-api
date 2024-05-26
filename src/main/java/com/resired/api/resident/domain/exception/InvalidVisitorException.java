package com.resired.api.resident.domain.exception;

public class InvalidVisitorException extends RuntimeException {
    public InvalidVisitorException(String document) {
        super("Visitor " + document + " is not valid");
    }
}
