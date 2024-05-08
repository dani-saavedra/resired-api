package com.resired.api.security.application.usecase;

import com.resired.api.security.application.dto.AuthenticationRequest;
import com.resired.api.security.application.dto.AuthenticationResponse;
import com.resired.api.security.application.exception.InactiveUserException;
import com.resired.api.security.application.exception.InvalidCredentialException;
import com.resired.api.security.domain.entity.Resident;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.domain.service.AuthenticationService;
import java.security.GeneralSecurityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUseCase {

    private final AuthenticationService authService;
    private final UserPort userPort;

    public AuthenticationResponse authUser(AuthenticationRequest auth) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(auth.password());

        Resident resident = userPort.getResidentByCredentials(auth.email(), encryptPass);
        if (resident == null) {
            throw new InvalidCredentialException();
        }
        if (!resident.isActive()) {
            throw new InactiveUserException(resident.getUserId());
        }

        String jwt = Jwt.generateToken(resident.getProfile(), resident.getUserId());
        return new AuthenticationResponse(jwt, resident.getProfile(), resident.getUserName(),
            resident.getUserId(), resident.isMandatoryChangePassword());
    }

}
