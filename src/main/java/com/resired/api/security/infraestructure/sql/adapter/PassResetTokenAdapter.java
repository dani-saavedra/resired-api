package com.resired.api.security.infraestructure.sql.adapter;

import com.resired.api.security.domain.repository.PassResetTokenPort;
import com.resired.api.security.infraestructure.sql.jpa.PasswordResetTokenJpa;
import com.resired.api.security.infraestructure.sql.orm.PasswordResetTokenOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class PassResetTokenAdapter implements PassResetTokenPort {

    private PasswordResetTokenJpa jpaRepository;

    @Override
    public void savePassResetToken(Integer userId, String token, LocalDateTime expirationTime) {
        PasswordResetTokenOrm passwordResetTokenOrm = new PasswordResetTokenOrm(userId, token, expirationTime);
        jpaRepository.save(passwordResetTokenOrm);
    }
}
