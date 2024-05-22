package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.exception.QrInvalidException;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.security.domain.service.JwtSecurity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidateVisitUseCase {

    private final GuardPort guardPort;
    private final JwtSecurity jwtSecurity;

    public Visitor validateInfoQR(String qr) {
        boolean valid = jwtSecurity.validateJwt(qr);
        if (!valid) {
            throw new QrInvalidException("Signature");
        }
        Visitor visitor = guardPort.obtainInfoQR(qr);
        if (!visitor.availableToEnter()) {
            throw new QrInvalidException("available");
        }
        return visitor;
    }
}
