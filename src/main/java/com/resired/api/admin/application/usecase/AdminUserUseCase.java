package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.GuardDto;
import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.admin.domain.entity.Resident;
import com.resired.api.admin.application.repository.AdminGuardPort;
import com.resired.api.admin.domain.repository.AdminUserPort;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.resident.domain.repository.NeighborhoodPort;
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
    private final AdminGuardPort adminGuardPort;
    private final AuthenticationService authService;
    private final EmailPort emailPort;
    private final NeighborhoodPort neighborhoodPort;

    public void registerUserToNeighborhood(RegisterUserVO registerUserVO, String registeredBy) throws GeneralSecurityException {
        String password = authService.encrypt(registerUserVO.documentId());
        Integer userId = adminUserPort.getUserByEmail(registerUserVO.email());
        Neighborhood neighborhood = neighborhoodPort.findById(registerUserVO.neighborhoodId());
        if (userId == null) {
            adminUserPort.registerUserToNeighborhood(registerUserVO, registeredBy, password);
            emailPort.sendRegisteredUserEmail(registerUserVO.email(), neighborhood.getName());
        } else {
            adminUserPort.associateNewUserToNeighborhood(registerUserVO, userId);
            emailPort.sendAssociateNewUserToNeighborhood(registerUserVO.email(), neighborhood.getName());
        }
    }

    public void removeResidentByUserId(Integer neighborhoodId, Integer userId) {
        adminUserPort.removeUserById(neighborhoodId, userId);
    }

    public List<GuardDto> getAllGuards(Integer neighborhoodId, boolean active) {
        return adminGuardPort.getAllGuards(neighborhoodId, active ? 1 : 0);
    }

    public List<Resident> getResidentByNeighborhood(Integer neighborhoodId, boolean active) {
        return adminUserPort.getResidentByNeighborhood(neighborhoodId, active ? 1 : 0);
    }

    public List<Resident> getResidentByHome(Integer homeId) {
        return adminUserPort.getResidentByHome(homeId);
    }
}
