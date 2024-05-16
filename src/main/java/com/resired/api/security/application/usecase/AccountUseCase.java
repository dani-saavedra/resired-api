package com.resired.api.security.application.usecase;

import com.resired.api.security.application.dto.ResetPasswordRequest;
import com.resired.api.security.application.exception.InvalidCredentialException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.domain.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
@AllArgsConstructor
public class AccountUseCase {

    private final UserPort userPort;
    private final AuthenticationService authService;

    public void resetPassword(ResetPasswordRequest request) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(request.oldPassword());
        User user = userPort.getResidentByCredentials(request.email(), encryptPass);
        if (user == null) {
            throw new InvalidCredentialException();
        }
        String newEncryptPass = authService.encrypt(request.newPassword());
        userPort.changePassword(user.getUserId(),newEncryptPass);
    }
}
