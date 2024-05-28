package com.resired.api.shared.notification.application.usecase;

import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.exception.DeviceAlreadyExistsException;
import com.resired.api.shared.notification.domain.exception.DeviceNotFoundException;
import com.resired.api.shared.notification.domain.repository.DevicePort;
import com.resired.api.shared.notification.domain.repository.UserNotificationPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceUseCase {
    private final UserNotificationPort userPort;
    private final DevicePort devicePort;

    public void registerDevice(Device device, String email) {
        if (devicePort.alreadyExists(device.getId())) {
            throw new DeviceAlreadyExistsException(device.getId());
        }

        userPort.addDevice(email, device);
    }

    public void removeDevice(String deviceID, String email) {
        Device device = userPort.getDeviceByIDAndEmail(deviceID, email);
        if (device == null) {
            throw new DeviceNotFoundException(deviceID, email);
        }
        devicePort.removeDevice(deviceID);
    }
}
