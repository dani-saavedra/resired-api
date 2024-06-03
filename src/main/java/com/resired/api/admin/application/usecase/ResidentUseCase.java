package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.vo.RegisterResidentVO;
import com.resired.api.admin.domain.repository.ResidentPort;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.security.domain.repository.EmailPort;
import com.resired.api.security.domain.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class ResidentUseCase {

    private final ResidentPort residentPort;
    private final HomePort homePort;
    private final AuthenticationService authService;
    private final EmailPort emailPort;

    public void registerResident(RegisterResidentVO residentVO, String registeredBy) throws GeneralSecurityException {
        String password = authService.encrypt(residentVO.documentId());
        Integer userId = residentPort.getResidentIdByEmail(residentVO.email());
        if (userId == null) {
            residentPort.registerResident(residentVO, registeredBy, password);
            emailPort.sendRegisteredResidentEmail(residentVO.email());
        } else {
            residentPort.associateNewResidence(residentVO, userId);
            emailPort.sendAssociateNewResidentToResidentEmail(residentVO.email());
        }
    }

    public void removeResidentsByHome(Integer neighborhoodId, Integer idHome) {
        Home home = homePort.getHomeById(idHome);
        if(home == null || !Objects.equals(home.getNeighborhoodId(), neighborhoodId)){
            throw new InvalidHomeException(idHome);
        }
        residentPort.removeResidentByHome(idHome);
    }

    public void removeResidentByUserId(Integer neighborhoodId, Integer userId) {
        residentPort.removeResidentByUserId(neighborhoodId, userId);
    }
}
