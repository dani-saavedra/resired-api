package com.resired.api.security.domain.service;

import com.resired.api.security.domain.repository.RecoveryPassPort;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.PassResetTokenPort;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecoveryPasswordService {

    private final UserPort userPort;
    private final RecoveryPassPort recoveryPassPort;
    private final PassResetTokenPort passResetTokenPort;

    public void createPasswordResetTokenForUser(String email) {

        String token = UUID.randomUUID().toString();
        User user = userPort.getResidentByEmail(email);
        if (user == null) {
            throw new InactiveUserException(email);
        }
        passResetTokenPort.savePassResetToken(user.getId(), token, LocalDateTime.now().plusDays(1));
        recoveryPassPort.sendEmailWithToken(email, token);
    }
}
