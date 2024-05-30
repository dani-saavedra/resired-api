package com.resired.api.security.domain.repository;

import com.resired.api.security.domain.vo.PassResetTokenVo;

import java.time.LocalDateTime;

public interface PassResetTokenPort {

    void savePassResetToken(Integer userId, String token, LocalDateTime expirationTime);

    PassResetTokenVo findExpirationTimeByToken(String token);

    void invalidateToken(String token);
}
