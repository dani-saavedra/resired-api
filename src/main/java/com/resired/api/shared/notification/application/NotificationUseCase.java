package com.resired.api.shared.notification.application;

import com.resired.api.admin.application.dto.*;
import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.shared.notification.application.dto.NotificationBlockRequest;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.exception.NotificationCategoryNotFoundException;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationUseCase {
    private final NotificationMessagePort notificationRepository;
    private final NotificationCategoryPort notificationCategoryPort;
    private final PushAppUseCase pushNotificationUseCase;

    public List<NotificationResponseDto> getAllNotificationsByNeighborhoodId(Integer neighborhoodId) {
        return notificationRepository.getAllNotificationMessagesByNeighborhoodId(neighborhoodId)
            .stream()
            .map(notificationMessage ->
                new NotificationResponseDto(notificationMessage.id(),
                    notificationMessage.title(),
                    notificationMessage.message(),
                    notificationMessage.date(),
                    notificationMessage.category().name(),
                    notificationMessage.level().name()))
            .toList();
    }

    public void sendNotificationToNeighborhood(CreateNotificationDto requestDto, Integer neighborhoodId) {
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

    public List<NotificationCategoryDto> getAllNotificationCategories(Integer neighborhoodId) {
        return notificationCategoryPort.getAllCategoriesByNeighborhoodId(neighborhoodId)
            .stream()
            .map(category -> new NotificationCategoryDto(
                category.id(),
                category.name(),
                category.defaultMessage(),
                category.level(),
                category.defaultTitle()))
            .toList();
    }

    public void createNotificationCategory(NotificationCategoryRequestDto requestDto, Integer neighborhoodId) {
        NotificationCategory category = new NotificationCategory(null, neighborhoodId,
            requestDto.name(), requestDto.defaultMessage(), requestDto.priority(),
            requestDto.defaultTitle());

        notificationCategoryPort.createNewNotificationCategory(category);
    }

    public void sendNotificationToBlocks(CreateNotificationForBlocksDto requestDto) {
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

    public void createNotificationForHomes(CreateNotificationForHomesDto requestDto) {
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
