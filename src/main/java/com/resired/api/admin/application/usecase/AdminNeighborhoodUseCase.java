package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.exception.BusinessException;
import com.resired.api.admin.application.exception.InvalidConfigurationException;
import com.resired.api.admin.application.repository.AdminNewsPort;
import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.BlockPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.*;
import com.resired.api.admin.infraestructure.gcp.FileBucket;
import com.resired.api.security.application.dto.RefreshResponse;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.Rol;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class AdminNeighborhoodUseCase {

    private final NotificationCategoryPort notificationCategoryPort;
    private final AdminNeighborhoodPort adminNeighborhoodPort;
    private final AdminNewsPort adminNewsPort;
    private final AdminUserUseCase adminUserUseCase;
    private final BlockPort blockPort;
    private final PushAppUseCase pushAppUseCase;
    private final UserPort userPort;
    private final JwtService jwtService;
    private final FileBucket fileBucket;


    private static final String BUCKET_IMAGES_NAME = "cover_image_resired";
    private static final String BUCKET_ATTACHMENT_NAME = "attachment_resired";

    public void createNewNeighborhood(CreateNeighborhoodVo createNeighborhoodVo) throws GeneralSecurityException {
        Integer idNewNeigh = adminNeighborhoodPort.createNeighborHood(createNeighborhoodVo);
        associateDefaultCategories(idNewNeigh);

        CreateNeighborhoodVo.AdminUser admin = createNeighborhoodVo.admin();
        RegisterUserVO registerUserVO = new RegisterUserVO(admin.document(), admin.documentType(), "Admin", null,
            admin.email(), idNewNeigh, null, UserType.ADMIN);
        adminUserUseCase.registerUserToNeighborhood(registerUserVO, "resired", true);

    }

    private void associateDefaultCategories(Integer idNewNeigh) {
        for (DefaultNotificationCategory category : DefaultNotificationCategory.values()) {
            NotificationCategory notificationCategory = new NotificationCategory(null, idNewNeigh,
                category.name(), category.getDefaultMessage(), LevelNotificationEnum.MEDIUM,
                category.getDefaultTitle());
            notificationCategoryPort.createNewNotificationCategory
                (notificationCategory);
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
        if (neighConfig.groupingHomes().isEmpty()) {
            blockPort.createBlock(neighborhood.getId(), neighConfig.groupingType(),
                "CONJUNTO", new ArrayList<>());
        } else {
            for (NeighConfig.GroupingHomes groupingHome : neighConfig.groupingHomes()) {
                blockPort.createBlock(neighborhood.getId(), neighConfig.groupingType(),
                    groupingHome.tower(), groupingHome.homes());
            }
        }

    }

    public void createNewsV1(CreateNewsDto newsRequest, Integer neighborhoodId) {
        adminNewsPort.createNews(newsRequest, neighborhoodId, "", "");

        NotificationNeighborhoodRequest requestDTO = new NotificationNeighborhoodRequest("¡Novedad en tu conjunto!",
            newsRequest.title(), neighborhoodId);
        pushAppUseCase.notifyNeighborhood(requestDTO);
    }

    public void createNewsV2(CreateNewsDto newsRequest, Integer neighborhoodId) throws IOException {
        String imageUrl = null;
        String detail = null;
        if (newsRequest.image() != null) {
            String name = neighborhoodId + "-" + newsRequest.title().trim().replaceAll(" ", "") + "-" + newsRequest.image().name().trim();
            imageUrl = fileBucket.uploadFileToBucket(BUCKET_IMAGES_NAME, name, newsRequest.image().inputStream());
        }
        if (newsRequest.details() != null) {
            String name = neighborhoodId + "-" + newsRequest.title().trim().replaceAll(" ", "") + "-" + newsRequest.details().name().trim();
            detail = fileBucket.uploadFileToBucket(BUCKET_ATTACHMENT_NAME, name, newsRequest.details().inputStream());
        }
        adminNewsPort.createNews(newsRequest, neighborhoodId, imageUrl, detail);

        NotificationNeighborhoodRequest requestDTO = new NotificationNeighborhoodRequest("¡Novedad en tu conjunto!",
            newsRequest.title(), neighborhoodId);
        pushAppUseCase.notifyNeighborhood(requestDTO);
    }

    public RefreshResponse chooseNeighborhood(Integer neighborhoodId, Integer integer) {
        User user = userPort.getUserById(integer);

        Rol rol = user.getRoles().stream()
            .filter(n -> UserType.ADMIN.equals(n.getUserType()) && Objects.equals(n.getNeighborhoodId(), neighborhoodId))
            .findFirst()
            .orElseThrow(() -> new BusinessException("Neighborhood not found", "GENERAL_BAD_REQUEST"));

        String accessToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 2);
        String refreshToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 4);

        return new RefreshResponse(accessToken, refreshToken);
    }
}
