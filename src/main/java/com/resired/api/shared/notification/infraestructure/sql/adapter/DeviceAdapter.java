package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.security.domain.enums.UserType;
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

    @Override
    public Boolean alreadyExists(String deviceID) {
        Optional<DeviceOrm> device = deviceRepository.findById(deviceID);
        return device.isPresent();
    }

    @Override
    public void removeDevice(String deviceID) {
        deviceRepository.deleteById(deviceID);
    }

    @Override
    public List<Device> getDevicesForHomeResident(Integer homeID) {
        return deviceRepository.findDevicesByHomeIdAndUserRole(homeID, UserType.RESIDENT)
            .stream()
            .map(deviceOrm -> new Device(deviceOrm.getId()))
            .toList();
    }

    @Override
    public Device getDeviceByUser(Integer userId) {
        DeviceOrm orm = deviceRepository.findFirstByUserId(userId);
        if (orm != null) {
            return new Device(orm.getId());
        }
        return null;
    }
}
