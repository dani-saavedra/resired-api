package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.exception.QrInvalidException;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.security.domain.service.JwtSecurity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendVisitUseCase {

    private final GuardPort guardPort;
    private final QrPort qrPort;
    private final JwtSecurity jwtSecurity;

    public Visitor validateInfoQR(String qr) {
        validateQR(qr);
        return qrPort.obtainInfoQR(qr);
    }

    public void registerVisit(String qr) {
        validateQR(qr);
        guardPort.registerVisit(qr);
        qrPort.makeQrUnavailable(qr);

    }

    private void validateQR(String qr) {
        boolean valid = jwtSecurity.validateJwt(qr);
        if (!valid) {
            throw new QrInvalidException("Signature");
        }
        if (!qrPort.isAvailableQR(qr)) {
            throw new QrInvalidException("available");
        }
    }

    //TODO REGISTRAR VISITANTE Y VISITA EN UN MISMO PUNTO, osea sin QR
}
