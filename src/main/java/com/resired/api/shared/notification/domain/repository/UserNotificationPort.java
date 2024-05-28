package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.Device;

import java.util.List;

public interface UserNotificationPort {
    void addDevice(String email, Device device);

    List<Device> getAllDevicesByEmail(String email);
}
