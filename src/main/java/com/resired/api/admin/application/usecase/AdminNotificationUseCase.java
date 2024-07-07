package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.*;
import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.shared.notification.application.dto.NotificationBlockRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.exception.NotificationCategoryNotFoundException;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminNotificationUseCase {
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
            requestDto.message(), null, category, requestDto.priority());

        pushNotificationUseCase.notifyNeighborhood(pushNotification);
        notificationRepository.saveNotification(notificationMessage);
    }

    public List<NotificationCategoryResponseDto> getAllNotificationCategories(Integer neighborhoodId) {
        return notificationCategoryPort.getAllCategoriesByNeighborhoodId(neighborhoodId)
            .stream()
            .map(notificationCategory -> new NotificationCategoryResponseDto(notificationCategory.id(),
                notificationCategory.name()))
            .toList();
    }

    public NotificationCategoryDto getNotificationCategory(Integer categoryId, Integer neighborhoodId) {
        NotificationCategory category = notificationCategoryPort
            .getNotificationCategoryByIdAndNeighborhoodId(categoryId, neighborhoodId);

        if (category == null) return null;

        return new NotificationCategoryDto(
            category.id(),
            category.name(),
            category.defaultMessage(),
            category.level(),
            category.defaultTitle());
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
            requestDto.message(), null, category, requestDto.priority());

        requestDto.blocksId().forEach(blockId -> {
            NotificationBlockRequest pushNotification = new NotificationBlockRequest(requestDto.title(),
                requestDto.message(), blockId);
            pushNotificationUseCase.notifyBlock(pushNotification);
        });
        notificationRepository.saveNotification(notificationMessage);

    }
}
