package com.resired.api.shared.notification.application.usecase;

import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.exception.DeviceAlreadyExistsException;
import com.resired.api.shared.notification.domain.exception.DeviceNotFoundException;
import com.resired.api.shared.notification.domain.repository.DevicePort;
import com.resired.api.shared.notification.domain.repository.UserNotificationPort;
import com.resired.api.shared.notification.domain.service.NotificationSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceUseCase {
    private final UserNotificationPort userPort;
    private final DevicePort devicePort;
    private final NotificationSender notificationSenderService;

    public void registerDevice(Device device, String email) {
        if (devicePort.alreadyExists(device.getId())) {
            throw new DeviceAlreadyExistsException(device.getId());
        }

        userPort.addDevice(email, device);

        List<Integer> neighborhoodIds = userPort.getNeighborhoodIdsByEmail(email);

        neighborhoodIds.forEach((neigh) -> {
            String topic = "/topics/neighborhoods/" + neigh;
            notificationSenderService.subscribeDeviceToTopic(device, topic);
        });
    }

    public void removeDevice(String deviceID, String email) {
        Device device = userPort.getDeviceByIDAndEmail(deviceID, email);
        if (device == null) {
            throw new DeviceNotFoundException(deviceID, email);
        }
        devicePort.removeDevice(deviceID);

        List<Integer> neighborhoodIds = userPort.getNeighborhoodIdsByEmail(email);

        neighborhoodIds.forEach((neigh) -> {
            String topic = "/topics/neighborhoods/" + neigh;
            notificationSenderService.unsubscribeDeviceToTopic(device, topic);
        });

    }

    public Device getDevice(String deviceID, String email) {
        Device device = userPort.getDeviceByIDAndEmail(deviceID, email);
        if (device == null) {
            throw new DeviceNotFoundException(deviceID, email);
        }
        return device;
    }

    public List<Device> getDevicesByUser(String email) {
        return userPort.getAllDevicesByEmail(email);
    }
}
