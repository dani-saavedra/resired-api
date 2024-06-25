package com.resired.api.admin.infraestructure.exception;

import lombok.Getter;

@Getter
public class InvalidMultipartFileException extends RuntimeException {
    private final String code;

    public InvalidMultipartFileException(String code) {
        super("Multipart file not valid");
        this.code = code;
    }
}
