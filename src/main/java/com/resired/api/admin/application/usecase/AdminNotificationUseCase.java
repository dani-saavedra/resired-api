package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.NotificationResponseDto;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminNotificationUseCase {
    private final NotificationMessagePort notificationRepository;

    public List<NotificationResponseDto> getAllNotificationsByNeighborhoodId(Integer neighborhoodId) {
        return notificationRepository.getAllNotificationMessagesByNeighborhoodId(neighborhoodId)
            .stream()
            .map(notificationMessage ->
                new NotificationResponseDto(notificationMessage.id(),
                    notificationMessage.title(),
                    notificationMessage.message(),
                    notificationMessage.date()))
            .toList();
    }
}
