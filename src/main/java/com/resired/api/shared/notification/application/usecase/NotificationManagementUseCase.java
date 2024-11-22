package com.resired.api.shared.notification.application.usecase;

import com.resired.api.admin.application.dto.NotificationCategoryDto;
import com.resired.api.admin.application.dto.NotificationCategoryRequestDto;
import com.resired.api.admin.application.dto.NotificationResponseDto;
import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationManagementUseCase {

    private final NotificationMessagePort notificationRepository;
    private final NotificationCategoryPort notificationCategoryPort;


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

}
