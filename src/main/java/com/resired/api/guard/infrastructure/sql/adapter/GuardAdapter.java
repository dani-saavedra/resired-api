package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.vo.VisitMade;
import com.resired.api.guard.infrastructure.sql.dto.VisitorWithQrDto;
import com.resired.api.guard.infrastructure.sql.jpa.VisitJpaRepository;
import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.VisitorJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GuardAdapter implements GuardPort {

    private final QrJpaRepository qrJpaRepository;
    private final VisitJpaRepository visitJpaRepository;
    private final VisitorJpaRepository visitorJpa;

    @Override
    public VisitMade registerVisit(String qrStr, Integer guardId, String plateNumber) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);

        VisitOrm visit = new VisitOrm();
        visit.setQr(qr);
        LocalDateTime checkin = LocalDateTime.now(ZoneOffset.UTC);
        visit.setCheckIn(checkin);

        if (plateNumber != null) {
            visit.setPlateCarNumber(plateNumber);
            visit.setIsCarActive(true);
        } else {
            visit.setIsCarActive(false);
        }

        UserOrm guard = new UserOrm();
        guard.setId(guardId);
        visit.setAuthorizingGuard(guard);
        makeQrUnavailable(qr);
        visitJpaRepository.save(visit);
        UserOrm authorizingUser = qr.getVisitor().getAuthorizingUser();
        if (authorizingUser != null) {
            return new VisitMade(authorizingUser.getId(), qr.getVisitor().getName(), checkin);
        } else {
            return new VisitMade(null, qr.getVisitor().getName(), checkin);
        }
    }

    @Override
    public String registerVisitFromGuard(Integer guardId, Integer homeId, String homeName,
                                         String visitorName, String visitorDocument, String visitorTelephone) {
        String tokenUUID = UUID.randomUUID().toString();
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromGuard(guardId, homeId, homeName,
            visitorName, visitorDocument, visitorTelephone));
        QrOrm qr = new QrOrm();
        qr.setVisitor(visitor);
        qr.setAvailable(false);
        qr.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        qr.setDisabledAt(LocalDateTime.now(ZoneOffset.UTC));
        qr.setQr(tokenUUID);
        qrJpaRepository.save(qr);
        return tokenUUID;
    }

    @Override
    public void updateVisitorDocument(String qrStr, String visitorDocument) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);
        qr.getVisitor().setDocument(visitorDocument);
        visitorJpa.save(qr.getVisitor());
    }

    @Override
    public List<Visit> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                              LocalDateTime startDate,
                                                              LocalDateTime endDate) {
        List<VisitOrm> visitOrms = visitJpaRepository.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId,
            startDate, endDate);
        return visitOrms.stream().map(this::toVisitDomain).toList();
    }

    @Override
    public List<Visitor> findAllVisitorsWithActiveQr(Integer neighborhoodId) {
        List<VisitorWithQrDto> list = visitorJpa.findAllWithActiveQr(neighborhoodId);
        return list
            .stream()
            .filter(obj -> obj.getQr().isAvailableToEnter())
            .map(this::toVisitorDomain)
            .toList();
    }



    private void makeQrUnavailable(QrOrm qr) {
        if (!qr.getVisitor().isFavorite()) {
            qr.setAvailable(false);
            qr.setDisabledAt(LocalDateTime.now(ZoneOffset.UTC));
            qrJpaRepository.save(qr);
        }
    }

    private Visit toVisitDomain(VisitOrm visitOrm) {
        String guardFullName = visitOrm.getAuthorizingGuard().toEntity().getFullNameLastOneFirst();
        String destination = visitOrm.getQr().getVisitor().getAuthorizingHome().toBasicInfoHome().getFullHomeName();

        return new Visit(
            visitOrm.getId(),
            visitOrm.getQr().getVisitor().getName(),
            visitOrm.getQr().getVisitor().getDocument(),
            destination,
            visitOrm.getCheckIn(),
            guardFullName
        );
    }

    private Visitor toVisitorDomain(VisitorWithQrDto visitorWithQr) {
        return new Visitor(
            visitorWithQr.getVisitor().getName(),
            visitorWithQr.getVisitor().getDocument(),
            visitorWithQr.getVisitor().getAuthorizingHome().toBasicInfoHome().getFullHomeName(),
            visitorWithQr.getQr().getQr()
        );
    }
}
