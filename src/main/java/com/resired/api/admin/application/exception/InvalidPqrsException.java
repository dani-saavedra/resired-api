package com.resired.api.admin.application.exception;

public class InvalidPqrsException extends BusinessException {

    public InvalidPqrsException(String code) {
        super("The PQRS is not valid or does not exist for this neighborhood", code);
    }
}
