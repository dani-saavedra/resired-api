package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.GuardResponseDto;
import com.resired.api.admin.domain.repository.AdminUserPort;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.security.domain.repository.EmailPort;
import com.resired.api.security.domain.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AdminUserUseCase {

    private final AdminUserPort adminUserPort;
    private final AuthenticationService authService;
    private final EmailPort emailPort;

    public void registerUserToNeighborhood(RegisterUserVO registerUserVO, String registeredBy) throws GeneralSecurityException {
        String password = authService.encrypt(registerUserVO.documentId());
        Integer userId = adminUserPort.getUserByEmail(registerUserVO.email());
        if (userId == null) {
            adminUserPort.registerUserToNeighborhood(registerUserVO, registeredBy, password);
            emailPort.sendRegisteredUserEmail(registerUserVO.email());
        } else {
            adminUserPort.associateNewUserToNeighborhood(registerUserVO, userId);
            emailPort.sendAssociateNewUserToNeighborhood(registerUserVO.email());
        }
    }

    public void removeResidentByUserId(Integer neighborhoodId, Integer userId) {
        adminUserPort.removeUserById(neighborhoodId, userId);
    }

    public List<GuardResponseDto> getAllGuards(Integer neighborhoodId, boolean active) {
        List<RegisterUserVO> guards = adminUserPort.getAllGuards(neighborhoodId, active ? 1 : 0);
        return guards.stream()
            .map(guard -> new GuardResponseDto(
                guard.documentId(),
                guard.documentType(),
                guard.firstName() + " " + guard.lastName(),
                guard.email()))
            .toList();
    }
}
