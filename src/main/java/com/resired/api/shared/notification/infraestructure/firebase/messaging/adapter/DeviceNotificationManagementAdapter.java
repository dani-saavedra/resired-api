package com.resired.api.shared.notification.infraestructure.firebase.messaging.adapter;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.service.DeviceNotificationManagement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DeviceNotificationManagementAdapter implements DeviceNotificationManagement {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void subscribeDeviceToTopic(Device device, String topic) {
        List<String> devicesID = Collections.singletonList(device.id());

        try {
            TopicManagementResponse response = firebaseMessaging.subscribeToTopic(devicesID, topic);
            log.debug("{} devices were subscribed successfully", response.getSuccessCount());
        } catch (FirebaseMessagingException ex) {
            log.error("Problem subscribing devices to topic ", ex);
        }
    }

    @Override
    public void unsubscribeDeviceToTopic(Device device, String topic) {
        List<String> devicesID = Collections.singletonList(device.id());

        try {
            TopicManagementResponse response = firebaseMessaging.unsubscribeFromTopic(devicesID, topic);
            log.debug("{} tokens were unsubscribed successfully", response.getSuccessCount());
        } catch (FirebaseMessagingException ex) {
            log.error("Problem unsubscribing devices to topic ", ex);
        }
    }
}
