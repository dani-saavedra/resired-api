package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.VisitResponseDTO;
import com.resired.api.guard.application.exception.QrInvalidException;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.guard.domain.service.VisitorGuardService;
import com.resired.api.guard.domain.vo.Visit;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.security.domain.service.JwtSecurity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GuardVisitUseCase {

    private final GuardPort guardPort;
    private final QrPort qrPort;
    private final JwtSecurity jwtSecurity;
    private final Visitor visitor;
    private final VisitorGuardService visitorGuardService;

    public Visitor validateInfoQR(String qr) {
        validateQR(qr);
        return qrPort.obtainInfoQR(qr);
    }

    public void registerVisit(String qr) {
        validateQR(qr);
        guardPort.registerVisit(qr);
        qrPort.makeQrUnavailable(qr);
    }

    public void registerVisitor(String emailGuard, VisitorRequestDTO visitorDto) {
        Visit residentVisit = new Visit(visitorDto.name(), visitorDto.documentId(), visitorDto.telephone(),
            visitorDto.homeId(), emailGuard, false);
        String tokenUUID = visitor.createVisit(residentVisit, visitorGuardService);
        guardPort.registerVisit(tokenUUID);
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

    public List<VisitResponseDTO> getRecentVisits(Integer neighborhoodId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);
    }

    public List<VisitResponseDTO> getVisitsByDate(Integer neighborhoodId, LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(LocalTime.MAX);
        return guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);
    }
}
