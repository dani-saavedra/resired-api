package com.resired.api.shared.notification.domain.exception;

public class DeviceAlreadyExistsException extends RuntimeException {
    public DeviceAlreadyExistsException(String userID) {
        super("The device with ID " + userID + " already exists");
    }
}
