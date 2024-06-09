package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.CreateNeighborhood;
import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminNeighborhoodUseCase {

    public static final String DEFAULT_NOTIFICATION = "GENERAL";

    private final NotificationCategoryPort notificationCategoryPort;
    private final AdminNeighborhoodPort adminNeighborhoodPort;


    public void createNewNeighborhood(CreateNeighborhood createNeighborhood) {
        Integer idNewNeigh = adminNeighborhoodPort.createNeighborHood(createNeighborhood.name(), createNeighborhood.city(),
            createNeighborhood.address(), createNeighborhood.stratum(), createNeighborhood.securityCompany());
        notificationCategoryPort.createNewNotificationCategory
            (idNewNeigh, DEFAULT_NOTIFICATION, LevelNotificationEnum.MEDIUM);
    }
}
