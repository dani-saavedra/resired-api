package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.resident.domain.enums.DocumentTypeEnum;
import com.resired.api.security.domain.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminNeighborhoodUseCaseTest {

    @InjectMocks
    private AdminNeighborhoodUseCase neighborhoodService; // Reemplaza con el nombre de tu clase real

    @Mock
    private AdminNeighborhoodPort adminNeighborhoodPort;

    @Mock
    private AdminUserUseCase adminUserUseCase;

    @Mock
    private NotificationCategoryPort notificationCategoryPort;

    @Test
    void createNewNeighborhoodTest() throws GeneralSecurityException {
        // Arrange
        Integer expectedNeighborhoodId = 1;

        // Mocking `createNeighborHood` to return a neighborhood ID
        Mockito.when(adminNeighborhoodPort.createNeighborHood(Mockito.any(CreateNeighborhoodVo.class))).thenReturn(expectedNeighborhoodId);

        // Mock input
        CreateNeighborhoodVo.AdminUser adminUser = new CreateNeighborhoodVo.AdminUser("admin@test.com" , DocumentTypeEnum.CC, "12345");
        CreateNeighborhoodVo createNeighborhoodVo = new CreateNeighborhoodVo("", "", "", 1, "", null, adminUser);

        // Act
        neighborhoodService.createNewNeighborhood(createNeighborhoodVo);

        // Assert `createNeighborHood` call
        Mockito.verify(adminNeighborhoodPort).createNeighborHood(createNeighborhoodVo);

        // Capture and validate the `RegisterUserVO` argument
        ArgumentCaptor<RegisterUserVO> registerUserVOCaptor = ArgumentCaptor.forClass(RegisterUserVO.class);
        Mockito.verify(adminUserUseCase).registerUserToNeighborhood(registerUserVOCaptor.capture(), Mockito.eq("resired"), Mockito.eq(true));

        RegisterUserVO capturedRegisterUserVO = registerUserVOCaptor.getValue();

        assertEquals("12345", capturedRegisterUserVO.documentId());
        assertEquals(DocumentTypeEnum.CC, capturedRegisterUserVO.documentType());
        assertEquals("admin@test.com", capturedRegisterUserVO.email());
        assertEquals(expectedNeighborhoodId, capturedRegisterUserVO.neighborhoodId());
        assertEquals(UserType.ADMIN, capturedRegisterUserVO.userType());
    }
}
