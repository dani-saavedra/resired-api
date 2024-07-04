package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.Device;

import java.util.List;

public interface DevicePort {
    Boolean alreadyExists(String deviceID);

    void removeDevice(String deviceID);

    List<Device> getDevicesForHomeResident(Integer homeID);

    Device getDeviceByUser(Integer userId);

}
