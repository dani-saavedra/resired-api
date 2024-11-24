package com.resired.api.shared.notification.application.usecase;

import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.PushNotification;
import com.resired.api.shared.notification.domain.exception.DeviceAlreadyExistsException;
import com.resired.api.shared.notification.domain.exception.DeviceNotFoundException;
import com.resired.api.shared.notification.domain.port.ManageSubscriptionsTopicPort;
import com.resired.api.shared.notification.domain.port.PushNotificationPort;
import com.resired.api.shared.notification.domain.repository.DeviceManagementPort;
import com.resired.api.shared.notification.domain.repository.DeviceQuery;
import com.resired.api.shared.notification.domain.repository.MassNotificationQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceUseCase {

    private final MassNotificationQueryPort userPort;
    private final DeviceQuery deviceQuery;
    private final DeviceManagementPort deviceManagementPort;
    private final PushNotificationPort pushNotificationPortService;
    private final ManageSubscriptionsTopicPort manageSubscriptionsTopicPort;
    @Value("${topic.neighborhood}")
    private String NEIGHBORHOOD_TOPIC;

    @Value("${topic.block}")
    private String BLOCK_TOPIC;

    public void registerDevice(Device device, String email, Integer userId) {

        if (deviceManagementPort.alreadyExists(device.id())) {
            throw new DeviceAlreadyExistsException(device.id());
        }
        deviceManagementPort.addDevice(userId, device);
        List<Integer> neighborhoodIds = userPort.getNeighborhoodIdsForResidentByEmail(email);
        neighborhoodIds.forEach(neigh -> {
            String topic = NEIGHBORHOOD_TOPIC + neigh;
            manageSubscriptionsTopicPort.subscribeDeviceToTopic(device, topic);
        });
        PushNotification notificationMessage = new PushNotification("Bienvenid@", "En ResiRed estamos para servirte");
        pushNotificationPortService.sendToDevice(notificationMessage, device);

        List<BlockVo> blocks = userPort.getAllBlocksByUserEmail(email);
        blocks.forEach((blockVo -> {
            String topic = BLOCK_TOPIC + blockVo.id();
            manageSubscriptionsTopicPort.subscribeDeviceToTopic(device, topic);
        }));
    }

    public void removeDevice(String deviceID, String email) {
        Device device = deviceQuery.getDeviceByIDAndEmail(deviceID, email);
        if (device == null) {
            throw new DeviceNotFoundException(deviceID, email);
        }
        deviceManagementPort.removeDevice(deviceID);

        List<Integer> neighborhoodIds = userPort.getNeighborhoodIdsForResidentByEmail(email);
        neighborhoodIds.forEach(neigh -> {
            String topic = NEIGHBORHOOD_TOPIC + neigh;
            manageSubscriptionsTopicPort.unsubscribeDeviceToTopic(device, topic);
        });

    }

    public Device getDevice(String deviceID, String email) {
        Device device = deviceQuery.getDeviceByIDAndEmail(deviceID, email);
        if (device == null) {
            throw new DeviceNotFoundException(deviceID, email);
        }
        return device;
    }

    public List<Device> getDevicesByUser(String email) {
        return deviceQuery.getAllDevicesByEmail(email);
    }
}
