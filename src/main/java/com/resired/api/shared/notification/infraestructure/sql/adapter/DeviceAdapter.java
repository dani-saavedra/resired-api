package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.resident.infraestructure.sql.jpa.HomeJpaRepository;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.repository.DevicePort;
import com.resired.api.shared.notification.infraestructure.sql.jpa.DeviceJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeviceAdapter implements DevicePort {
    private final DeviceJpaRepository deviceRepository;
    private final HomeJpaRepository homeRepository;
    private final UserJpaRepository userRepository;

    @Override
    public Boolean alreadyExists(String deviceID) {
        Optional<DeviceOrm> device = deviceRepository.findById(deviceID);
        return device.isPresent();
    }

    @Override
    public Boolean hasNotificationsAllowed(String deviceID) {
        Optional<DeviceOrm> device = deviceRepository.findById(deviceID);
        return device.isPresent() ? device.get().getAllowNotifications() : false;
    }

    @Override
    public void updateNotificationPermission(String deviceID, Boolean status) {
        deviceRepository.changeNotificationPermission(deviceID, status);
    }

    @Override
    public void removeDevice(String deviceID) {
        deviceRepository.deleteById(deviceID);
    }

    @Override
    public List<Device> getDevicesForHomeResident(Integer homeID) {
        return deviceRepository.findDevicesByHomeIdAndUserRole(homeID, UserType.RESIDENT)
            .stream()
            .map(deviceOrm -> new Device(deviceOrm.getId(),
                deviceOrm.getAllowNotifications()))
            .toList();

    }
}
