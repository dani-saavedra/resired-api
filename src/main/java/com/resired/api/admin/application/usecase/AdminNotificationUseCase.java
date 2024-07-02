package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.NotificationResponseDto;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminNotificationUseCase {
    private final NotificationUseCase notificationUseCase;

    public List<NotificationResponseDto> getAllNotificationsByNeighborhoodId(Integer neighborhoodId) {
        return notificationUseCase.getAllNotificationsByNeighborhoodId(neighborhoodId).stream().map(notificationMessage ->
                new NotificationResponseDto(notificationMessage.id(),
                    notificationMessage.title(),
                    notificationMessage.message(),
                    notificationMessage.date()))
            .toList();
    }
}
