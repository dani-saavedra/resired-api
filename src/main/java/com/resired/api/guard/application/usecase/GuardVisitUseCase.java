package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.VisitResponseDTO;
import com.resired.api.guard.application.exception.QrInvalidException;
import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.guard.domain.vo.VisitMade;
import com.resired.api.guard.infrastructure.rest.dto.InfoQrRequest;
import com.resired.api.security.domain.service.JwtSecurity;
import com.resired.api.shared.notification.application.dto.NotificationResident;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
import com.resired.api.utils.FormatDate;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GuardVisitUseCase {

    private final GuardPort guardPort;
    private final QrPort qrPort;
    private final JwtSecurity jwtSecurity;
    private final NotificationUseCase notificationUseCase;

    public Visitor validateInfoQR(String qr) {
        validateQR(qr);
        return qrPort.obtainInfoQR(qr);
    }

    public void registerVisit(InfoQrRequest infoQrRequest, Integer guardId) {
        validateQR(infoQrRequest.qr());
        if (!qrPort.isAvailableQR(infoQrRequest.qr())) {
            throw new QrInvalidException("Unavailable");
        }
        if (!qrPort.hasDocumentRegisteredQR(infoQrRequest.qr())) {
            if (infoQrRequest.document() == null) {
                throw new QrInvalidException("Document");
            }
            guardPort.updateVisitorDocument(infoQrRequest.qr(), infoQrRequest.document());
        }
        VisitMade visitMade = guardPort.registerVisit(infoQrRequest.qr(), guardId, infoQrRequest.carPlateId());
        NotificationResident notification = new NotificationResident("¡Tu visita a llegado!",
            visitMade.visitor() + " ha presentado el QR en portería", visitMade.resident());
        notificationUseCase.notifyResident(notification);
    }

    private void validateQR(String qr) {
        try {
            jwtSecurity.validateJwt(qr);
        } catch (ExpiredJwtException e) {
            throw new QrInvalidException("Expired");
        } catch (Exception e) {
            log.error("QR presented has problems {}", e.getMessage());
            throw new QrInvalidException("Invalid");
        }
    }

    public List<VisitResponseDTO> getVisits(Integer neighborhoodId) {
        LocalDateTime endDate = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startDate = endDate.minusDays(1);
        List<Visit> visits = guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);

        return getVisitResponseDTOs(visits);
    }

    public List<VisitResponseDTO> getVisits(Integer neighborhoodId, LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(LocalTime.MAX);
        List<Visit> visits = guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);
        return getVisitResponseDTOs(visits);
    }

    private static List<VisitResponseDTO> getVisitResponseDTOs(List<Visit> visits) {
        return visits.stream().map(visit -> new VisitResponseDTO(
            visit.getId(),
            visit.getVisitorName(),
            visit.getVisitorDocument(),
            visit.getDestinationHome(),
            FormatDate.formatDate(visit.getCheckIn())
        )).toList();
    }
}
