package com.resired.api.shared.notification.application.usecase;

import com.resired.api.admin.application.dto.CreateNotificationDto;
import com.resired.api.admin.application.dto.CreateNotificationForBlocksDto;
import com.resired.api.admin.application.dto.CreateNotificationForHomesDto;
import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.shared.notification.application.dto.NotificationBlockRequest;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.exception.NotificationCategoryNotFoundException;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class NotificationUseCase {

    private final NotificationMessagePort notificationRepository;
    private final NotificationCategoryPort notificationCategoryPort;
    private final PushAppUseCase pushNotificationUseCase;


    public void sendNotification(CreateNotificationDto requestDto, Integer neighborhoodId) {
        NotificationNeighborhoodRequest pushNotification = new NotificationNeighborhoodRequest(requestDto.title(),
            requestDto.message(), neighborhoodId);

        NotificationCategory category = notificationCategoryPort.getNotificationCategoryById(requestDto.categoryId());

        if (category == null) throw new NotificationCategoryNotFoundException(requestDto.categoryId());

        NotificationMessage notificationMessage = new NotificationMessage(null, requestDto.title(),
            requestDto.message(), LocalDateTime.now(ZoneOffset.UTC).toString(),
            category, requestDto.priority());

        pushNotificationUseCase.notifyNeighborhood(pushNotification);
        notificationRepository.saveNotification(notificationMessage);
    }

    public void sendNotification(CreateNotificationForBlocksDto requestDto) {
        NotificationCategory category = notificationCategoryPort.getNotificationCategoryById(requestDto.categoryId());

        if (category == null) throw new NotificationCategoryNotFoundException(requestDto.categoryId());

        NotificationMessage notificationMessage = new NotificationMessage(null, requestDto.title(),
            requestDto.message(), LocalDateTime.now(ZoneOffset.UTC).toString(),
            category, requestDto.priority());

        requestDto.blocksId().forEach(blockId -> {
            NotificationBlockRequest pushNotification = new NotificationBlockRequest(requestDto.title(),
                requestDto.message(), blockId);
            pushNotificationUseCase.notifyBlock(pushNotification);
        });
        notificationRepository.saveNotification(notificationMessage);

    }

    public void sendNotification(CreateNotificationForHomesDto requestDto) {
        NotificationCategory category = notificationCategoryPort.getNotificationCategoryById(requestDto.categoryId());

        if (category == null) throw new NotificationCategoryNotFoundException(requestDto.categoryId());

        NotificationMessage notificationMessage = new NotificationMessage(null, requestDto.title(),
            requestDto.message(), LocalDateTime.now(ZoneOffset.UTC).toString(),
            category, requestDto.priority());

        requestDto.homesId().forEach(homeId -> {
            NotificationHomeRequest pushNotification = new NotificationHomeRequest(requestDto.title(),
                requestDto.message(), homeId);
            pushNotificationUseCase.notifyHome(pushNotification);
        });
        notificationRepository.saveNotification(notificationMessage);
    }
}
