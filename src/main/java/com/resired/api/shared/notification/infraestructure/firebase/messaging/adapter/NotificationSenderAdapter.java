package com.resired.api.shared.notification.infraestructure.firebase.messaging.adapter;

import com.google.firebase.messaging.*;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.service.NotificationSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationSenderAdapter implements NotificationSender {
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendToDevice(NotificationMessage notificationMessage, Device device) {
        Notification firebaseNotification
            = buildNotificationFromNotificationMessage(notificationMessage);
        Message message = Message
            .builder()
            .setToken(device.getId())
            .setNotification(firebaseNotification)
            .build();

        try {
            String response = firebaseMessaging.send(message);
            log.debug("Firebase notification sent successfully: {}", response);
        } catch (FirebaseMessagingException ex) {
            log.error("Problem sending message to firebase messaging ", ex);
        }
    }

    @Override
    public void sendToDeviceList(NotificationMessage notificationMessage, List<Device> devices) {
        List<String> devicesIDs = devices.stream().map(Device::getId).toList();

        Notification firebaseNotification
            = buildNotificationFromNotificationMessage(notificationMessage);

        MulticastMessage message = MulticastMessage
            .builder()
            .setNotification(firebaseNotification)
            .addAllTokens(devicesIDs)
            .build();

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = IntStream.range(0, responses.size())
                    .filter(i -> !responses.get(i).isSuccessful())
                    .mapToObj(devicesIDs::get).toList();

                log.error("List of tokens that caused failures: {}", failedTokens);
            }

            log.debug("{} messages were sent successfully by multicast", response.getSuccessCount());

        } catch (FirebaseMessagingException ex) {
            log.error("Problem sending message to firebase messaging multicast ", ex);
        }
    }

    @Override
    public void sendToTopic(NotificationMessage notificationMessage, String topic) {
        Notification firebaseNotification
            = buildNotificationFromNotificationMessage(notificationMessage);

        Message message = Message
            .builder()
            .setNotification(firebaseNotification)
            .setTopic(topic)
            .build();

        try {
            String response = firebaseMessaging.send(message);
            log.debug("Firebase notification sent successfully by topic: {}", response);
        } catch (FirebaseMessagingException ex) {
            log.error("Problem sending message to firebase messaging topic ", ex);
        }
    }

    @Override
    public void subscribeDeviceToTopic(Device device, String topic) {
        List<String> devicesID = Collections.singletonList(device.getId());

        try {
            TopicManagementResponse response = firebaseMessaging.subscribeToTopic(devicesID, topic);
            log.debug("{} devices were subscribed successfully", response.getSuccessCount());
        } catch (FirebaseMessagingException ex) {
            log.error("Problem subscribing devices to topic ", ex);
        }
    }

    @Override
    public void unsubscribeDeviceToTopic(Device device, String topic) {
        List<String> devicesID = Collections.singletonList(device.getId());

        try {
            TopicManagementResponse response = firebaseMessaging.unsubscribeFromTopic(devicesID, topic);
            log.debug("{} tokens were unsubscribed successfully", response.getSuccessCount());
        } catch (FirebaseMessagingException ex) {
            log.error("Problem unsubscribing devices to topic ", ex);
        }
    }

    private Notification buildNotificationFromNotificationMessage(NotificationMessage notificationMessage) {
        return Notification
            .builder()
            .setTitle(notificationMessage.title())
            .setBody(notificationMessage.message())
            .build();
    }
}
