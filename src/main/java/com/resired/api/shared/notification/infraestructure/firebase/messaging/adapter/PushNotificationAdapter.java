package com.resired.api.shared.notification.infraestructure.firebase.messaging.adapter;

import com.google.firebase.messaging.*;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.PushNotification;
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
public class PushNotificationAdapter implements NotificationSender {
    public static final String TITLE_NOTIFICATION = "title";
    public static final String BODY_NOTIFICATION = "body";
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendToDevice(PushNotification notificationMessage, Device device) {
        Notification firebaseNotification
            = buildNotificationFromNotificationMessage(notificationMessage);
        Message message = Message
            .builder()
            .setToken(device.id())
            .setNotification(firebaseNotification)
            .putData(TITLE_NOTIFICATION, notificationMessage.title())
            .putData(BODY_NOTIFICATION, notificationMessage.message())
            .build();

        try {
            String response = firebaseMessaging.send(message);
            log.debug("Firebase notification sent successfully: {}", response);
        } catch (FirebaseMessagingException ex) {
            log.error("Problem sending message to firebase messaging ", ex);
        }
    }

    @Override
    public void sendToDeviceList(PushNotification notificationMessage, List<Device> devices) {
        List<String> devicesIDs = devices.stream().map(Device::id).toList();

        Notification firebaseNotification
            = buildNotificationFromNotificationMessage(notificationMessage);

        MulticastMessage message = MulticastMessage
            .builder()
            .setNotification(firebaseNotification)
            .putData(TITLE_NOTIFICATION, notificationMessage.title())
            .putData(BODY_NOTIFICATION, notificationMessage.message())
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
    public void sendToTopic(PushNotification notificationMessage, String topic) {
        Notification firebaseNotification
            = buildNotificationFromNotificationMessage(notificationMessage);

        Message message = Message
            .builder()
            .setNotification(firebaseNotification)
            .putData(TITLE_NOTIFICATION, notificationMessage.title())
            .putData(BODY_NOTIFICATION, notificationMessage.message())
            .setTopic(topic)
            .build();

        try {
            String response = firebaseMessaging.send(message);
            log.debug("Firebase notification sent successfully by topic: {}", response);
        } catch (FirebaseMessagingException ex) {
            log.error("Problem sending message to firebase messaging topic ", ex);
        }
    }

    private Notification buildNotificationFromNotificationMessage(PushNotification notificationMessage) {
        return Notification
            .builder()
            .setTitle(notificationMessage.title())
            .setBody(notificationMessage.message())
            .build();
    }
}
