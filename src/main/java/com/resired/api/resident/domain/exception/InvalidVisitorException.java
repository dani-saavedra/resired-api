package com.resired.api.resident.domain.exception;

public class InvalidVisitorException extends RuntimeException {
    public InvalidVisitorException(Integer document) {
        super("Visitor " + document + " is not valid");
    }
}
