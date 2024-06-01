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
        residentPort.registerResident(residentVO, registeredBy, password);
        //TODO que va pasar cuando ya exista el usuario, se debe asociar al otro conjunto
        emailPort.sendRegisteredResidentEmail(residentVO.email());
    }
}
