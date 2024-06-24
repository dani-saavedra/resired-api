package com.resired.api.admin.application.exception;

public class InvalidHomesTemplateException extends BusinessException {

    public InvalidHomesTemplateException(String code) {
        super("Excel template not valid", code);
    }
}
