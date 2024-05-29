package com.resired.api.security.domain.repository;

import java.time.LocalDateTime;

public interface PassResetTokenPort {

    void savePassResetToken(Integer userId, String token, LocalDateTime expirationTime);
}
