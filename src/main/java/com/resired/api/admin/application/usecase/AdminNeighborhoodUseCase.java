package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.exception.BusinessException;
import com.resired.api.admin.application.exception.InvalidConfigurationException;
import com.resired.api.admin.application.repository.AdminNewsPort;
import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.BlockPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.*;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class AdminNeighborhoodUseCase {

    private final NotificationCategoryPort notificationCategoryPort;
    private final AdminNeighborhoodPort adminNeighborhoodPort;
    private final AdminNewsPort adminNewsPort;
    private final AdminUserUseCase adminUserUseCase;
    private final BlockPort blockPort;
    private final NotificationUseCase notificationUseCase;


    public void createNewNeighborhood(CreateNeighborhoodVo createNeighborhoodVo) throws GeneralSecurityException {
        Integer idNewNeigh = adminNeighborhoodPort.createNeighborHood(createNeighborhoodVo);
        associateDefaultCategories(idNewNeigh);

        CreateNeighborhoodVo.AdminUser admin = createNeighborhoodVo.admin();
        RegisterUserVO registerUserVO = new RegisterUserVO(admin.document(), admin.documentType(), "Admin", null,
            admin.email(), idNewNeigh, null, UserType.ADMIN);
        adminUserUseCase.registerUserToNeighborhood(registerUserVO, "resired");

    }

    private void associateDefaultCategories(Integer idNewNeigh) {
        for (DefaultNotificationCategory category : DefaultNotificationCategory.values()) {
            notificationCategoryPort.createNewNotificationCategory
                (idNewNeigh, category.name(), LevelNotificationEnum.MEDIUM);
        }
    }

    public void configNeighborhood(NeighConfig neighConfig) {
        //TODO q sea con el usuario, no como un parametro.
        Neighborhood neighborhood = adminNeighborhoodPort.findNeighborhoodById(neighConfig.id());
        if (neighborhood == null) {
            throw new BusinessException("Neighborhood not found", "GENERAL_BAD_REQUEST");
        }
        if (neighborhood.getHomes() != null && neighborhood.getHomes() > 0) {
            throw new BusinessException("Pre-configured neighborhood", "GENERAL_BAD_REQUEST");
        }
        AtomicInteger totalNumberHouses = new AtomicInteger();
        neighConfig.groupingHomes().forEach(groupingHomes -> totalNumberHouses.addAndGet(groupingHomes.homes().size()));

        if (neighborhood.getCategory().isInvalidQuantity(totalNumberHouses.intValue())) {
            throw new InvalidConfigurationException("NEIGHBORHOOD01");
        }
        int towers = neighConfig.groupingHomes().size();
        if (GroupingType.NINGUNA.equals(neighConfig.groupingType())) {
            towers = 0;
        }
        adminNeighborhoodPort.configNeighborhood(neighConfig, towers, totalNumberHouses.intValue());
        for (NeighConfig.GroupingHomes groupingHome : neighConfig.groupingHomes()) {
            blockPort.createBlock(neighborhood.getId(), neighConfig.groupingType(),
                groupingHome.tower(), groupingHome.homes());
        }
    }

    public void createNews(CreateNewsDto newsRequest, Integer neighborhoodId) {
        adminNewsPort.createNews(newsRequest, neighborhoodId);
        NotificationNeighborhoodRequest requestDTO = new NotificationNeighborhoodRequest("¡Novedad en tu conjunto!",
            newsRequest.title(), neighborhoodId);
        //TODO pendiente revisar si deberiamos guardar en la tabla de notification
        notificationUseCase.notifyNeighborhood(requestDTO);
    }
}
