package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.repository.DeviceManagementPort;
import com.resired.api.shared.notification.infraestructure.sql.jpa.DeviceJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeviceManagementAdapter implements DeviceManagementPort {

    private final DeviceJpaRepository deviceRepository;

    @Override
    public void addDevice(Integer userId, Device device) {
        deviceRepository.save(new DeviceOrm(device.id(), userId));
    }

    @Override
    public Boolean alreadyExists(String deviceID) {
        Optional<DeviceOrm> device = deviceRepository.findById(deviceID);
        return device.isPresent();
    }

    @Override
    public void removeDevice(String deviceID) {
        deviceRepository.deleteById(deviceID);
    }
}
