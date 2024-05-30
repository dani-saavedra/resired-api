package com.resired.api.security.application.usecase;

import com.resired.api.security.application.dto.RecoveryPasswordRequest;
import com.resired.api.security.application.dto.ResetPasswordRequest;
import com.resired.api.security.application.exception.ExpiredTokenException;
import com.resired.api.security.application.exception.InvalidCredentialException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.PassResetTokenPort;
import com.resired.api.security.domain.repository.EmailPort;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.domain.service.AuthenticationService;
import com.resired.api.security.domain.vo.PassResetTokenVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountUseCase {

    private final UserPort userPort;
    private final AuthenticationService authService;
    private final EmailPort emailPort;
    private final PassResetTokenPort passResetTokenPort;


    public void resetPassword(ResetPasswordRequest request) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(request.oldPassword());
        User user = userPort.getUserByCredentials(request.email(), encryptPass);
        if (user == null) {
            throw new InvalidCredentialException(request.email());
        }
        String newEncryptPass = authService.encrypt(request.newPassword());
        userPort.changePassword(user.getDocumentId(), newEncryptPass);
    }

    public void createPasswordResetTokenForUser(String email) {
        String token = UUID.randomUUID().toString();
        User user = userPort.getResidentByEmail(email);
        if (user == null) {
            throw new InactiveUserException(email);
        }
        passResetTokenPort.savePassResetToken(user.getId(), token, LocalDateTime.now().plusDays(1));
        emailPort.sendEmailToRecoverPass(email, token);
    }

    public void resetPassword(RecoveryPasswordRequest request) throws GeneralSecurityException {

        PassResetTokenVo passResetTokenVo = passResetTokenPort.findExpirationTimeByToken(request.token());
        if (passResetTokenVo == null
            || passResetTokenVo.expirationDate().isBefore(LocalDateTime.now())
            || passResetTokenVo.invalid()) {
            throw new ExpiredTokenException();
        }
        String newEncryptPass = authService.encrypt(request.password());
        userPort.changePassword(passResetTokenVo.userId(), newEncryptPass);
        passResetTokenPort.invalidateToken(request.token());
    }

}
