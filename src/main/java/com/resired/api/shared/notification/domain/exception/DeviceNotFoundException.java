package com.resired.api.shared.notification.domain.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String deviceID, String email) {
        super("User with email " + email +
            " do not have a device with ID" + deviceID);
    }
}
