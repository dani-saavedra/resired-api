package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;
import com.resired.api.admin.domain.vo.NeighConfig;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.security.domain.enums.UserType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
@AllArgsConstructor
public class AdminNeighborhoodUseCase {

    public static final String DEFAULT_NOTIFICATION = "GENERAL";

    private final NotificationCategoryPort notificationCategoryPort;
    private final AdminNeighborhoodPort adminNeighborhoodPort;
    private final AdminUserUseCase adminUserUseCase;


    public void createNewNeighborhood(CreateNeighborhoodVo createNeighborhoodVo) throws GeneralSecurityException {
        Integer idNewNeigh = adminNeighborhoodPort.createNeighborHood(createNeighborhoodVo);
        notificationCategoryPort.createNewNotificationCategory
            (idNewNeigh, DEFAULT_NOTIFICATION, LevelNotificationEnum.MEDIUM);


        CreateNeighborhoodVo.AdminUser admin = createNeighborhoodVo.admin();
        RegisterUserVO registerUserVO = new RegisterUserVO(admin.document(), admin.documentType(), "Admin", null,
            admin.email(), idNewNeigh, null, UserType.ADMIN);
        adminUserUseCase.registerUserToNeighborhood(registerUserVO, "resired");

    }

    public void configNeighborhood(NeighConfig neighConfig) {
        boolean validNumberHomes = neighConfig.category().validateQuantity(neighConfig.homes());
        if (!validNumberHomes) {
            throw new RuntimeException("Cantidad no valida de casas para esta categoria");//Pendiente crear Excepcion
        }
        adminNeighborhoodPort.configNeighborhood(neighConfig);
    }
}
