package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.CreateNeighborhood;
import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminNeighborhoodUseCase {

    private final NotificationCategoryPort notificationCategoryPort;
    private final AdminNeighborhoodPort adminNeighborhoodPort;

    private static final List<String> defaultCategories = new ArrayList<>();

    static {
        defaultCategories.add("GENERAL");
        defaultCategories.add("EVENTS");
    }

    public void createNewNeighborhood(CreateNeighborhood createNeighborhood) {
        Integer idNewNeigh = adminNeighborhoodPort.createNeighborHood(createNeighborhood.name(), createNeighborhood.city(), createNeighborhood.address());
        associateInitialCategories(idNewNeigh);
    }

    private void associateInitialCategories(Integer idNewNeigh) {
        defaultCategories.forEach(category -> {
            notificationCategoryPort.createNewNotificationCategory(idNewNeigh, category);
        });
    }
}
