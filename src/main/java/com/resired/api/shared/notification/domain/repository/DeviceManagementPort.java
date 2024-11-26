package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.Device;

public interface DeviceManagementPort {

    void addDevice(Integer userId, Device device);

    void removeDevice(String deviceID);

    Boolean alreadyExists(String deviceID);

}
