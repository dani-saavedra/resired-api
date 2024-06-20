package com.resired.api.admin.application.exception;

public class InvalidUserException extends BusinessException {

    public InvalidUserException(String code) {
        super("the user does not belong to this neighborhood", code);
    }
}
