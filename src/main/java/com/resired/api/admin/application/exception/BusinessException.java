package com.resired.api.admin.application.exception;

import lombok.Getter;
/*

    Use it when something happens that shouldn't happen
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
}
