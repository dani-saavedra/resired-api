package com.resired.api.security.domain.vo;

import java.time.LocalDateTime;

public record PassResetTokenVo(String token, Integer userId, LocalDateTime expirationDate, boolean invalid) {
}
