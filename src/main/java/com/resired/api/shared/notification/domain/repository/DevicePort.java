package com.resired.api.shared.notification.domain.repository;

public interface DevicePort {
    Boolean alreadyExists(String deviceID);

    Boolean hasNotificationsAllowed(String deviceID);

    void updateNotificationPermission(String deviceID, Boolean status);

    void removeDevice(String deviceID);

}
