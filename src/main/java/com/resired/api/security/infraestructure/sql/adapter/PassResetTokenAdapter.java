package com.resired.api.security.infraestructure.sql.adapter;

import com.resired.api.security.domain.repository.PassResetTokenPort;
import com.resired.api.security.domain.vo.PassResetTokenVo;
import com.resired.api.security.infraestructure.sql.jpa.PasswordResetTokenJpa;
import com.resired.api.security.infraestructure.sql.orm.PasswordResetTokenOrm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
@Transactional
public class PassResetTokenAdapter implements PassResetTokenPort {

    private PasswordResetTokenJpa jpaRepository;

    @Override
    public void savePassResetToken(Integer userId, String token, LocalDateTime expirationTime) {
        PasswordResetTokenOrm passwordResetTokenOrm = new PasswordResetTokenOrm(userId, token, expirationTime);
        jpaRepository.save(passwordResetTokenOrm);
    }

    @Override
    public PassResetTokenVo findExpirationTimeByToken(String strToken) {
        PasswordResetTokenOrm token = jpaRepository.findByToken(strToken);
        if (token != null) {
            return new PassResetTokenVo(token.getToken(), token.getUserId(), token.getExpiryDate(), token.isInvalid());
        }
        return null;
    }

    @Override
    public void invalidateToken(String token) {
        jpaRepository.updateToken(token);
    }
}
