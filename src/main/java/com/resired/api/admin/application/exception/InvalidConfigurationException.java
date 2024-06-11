package com.resired.api.admin.application.exception;

public class InvalidConfigurationException extends BusinessException {

    public InvalidConfigurationException(String code) {
        super("Neighborhood configuration is not allowed.", code);
    }
}
