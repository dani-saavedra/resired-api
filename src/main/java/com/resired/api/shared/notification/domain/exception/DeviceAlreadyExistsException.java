package com.resired.api.shared.notification.domain.exception;

public class DeviceAlreadyExistsException extends RuntimeException {
    public DeviceAlreadyExistsException(String deviceID) {
        super("The device with ID " + deviceID + " already exists");
    }
}
