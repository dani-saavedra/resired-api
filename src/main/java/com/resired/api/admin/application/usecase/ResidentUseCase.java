package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.vo.RegisterResidentVO;
import com.resired.api.admin.domain.repository.ResidentPort;
import com.resired.api.security.domain.repository.EmailPort;
import com.resired.api.security.domain.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
@AllArgsConstructor
public class ResidentUseCase {

    private final ResidentPort residentPort;
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
}
