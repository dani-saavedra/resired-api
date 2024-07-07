package com.resired.api.shared.notification.application.usecase;

import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.exception.DeviceAlreadyExistsException;
import com.resired.api.shared.notification.domain.exception.DeviceNotFoundException;
import com.resired.api.shared.notification.domain.repository.DevicePort;
import com.resired.api.shared.notification.domain.repository.UserNotificationPort;
import com.resired.api.shared.notification.domain.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceUseCase {
    private final UserNotificationPort userPort;
    private final DevicePort devicePort;
    private final NotificationSender notificationSenderService;
    @Value("${topic.neighborhood}")
    private String NEIGHBORHOOD_TOPIC;

    @Value("${topic.block}")
    private String BLOCK_TOPIC;

    public void registerDevice(Device device, String email, Integer userId) {
        if (devicePort.alreadyExists(device.id())) {
            throw new DeviceAlreadyExistsException(device.id());
        }

        userPort.addDevice(email, device);

        List<Integer> neighborhoodIds = userPort.getNeighborhoodIdsForResidentByEmail(email);

        neighborhoodIds.forEach((neigh) -> {
            String topic = NEIGHBORHOOD_TOPIC + neigh;
            notificationSenderService.subscribeDeviceToTopic(device, topic);
        });

        List<BlockVo> blocks = userPort.getAllBlocksByUserId(userId);

        blocks.forEach((blockVo -> {
            String topic = BLOCK_TOPIC + blockVo.id();
            notificationSenderService.subscribeDeviceToTopic(device, topic);
        }));

    }

    public void removeDevice(String deviceID, String email) {
        Device device = userPort.getDeviceByIDAndEmail(deviceID, email);
        if (device == null) {
            throw new DeviceNotFoundException(deviceID, email);
        }
        devicePort.removeDevice(deviceID);

        List<Integer> neighborhoodIds = userPort.getNeighborhoodIdsForResidentByEmail(email);
        neighborhoodIds.forEach((neigh) -> {
            String topic = NEIGHBORHOOD_TOPIC + neigh;
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
