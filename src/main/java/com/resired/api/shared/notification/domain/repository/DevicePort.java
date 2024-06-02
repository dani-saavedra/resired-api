package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.Device;

import java.util.List;

public interface DevicePort {
    Boolean alreadyExists(String deviceID);

    Boolean hasNotificationsAllowed(String deviceID);

    void updateNotificationPermission(String deviceID, Boolean status);

    void removeDevice(String deviceID);

    List<Device> getDevicesFromHomeOwner(Integer homeID);

}
