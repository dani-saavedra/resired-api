package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.Device;

import java.util.List;

public interface DeviceQuery {

    List<Device> getAllDevicesByEmail(String email);

    Device getDeviceByIDAndEmail(String deviceID, String email);

    List<Device> getDevicesForHomeResident(Integer homeID);

    List<Device> getDevicesByUser(Integer userId);
}
