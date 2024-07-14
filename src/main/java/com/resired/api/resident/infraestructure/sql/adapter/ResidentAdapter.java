package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.VisitorJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import com.resired.api.security.domain.service.JwtSecurity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Repository
@AllArgsConstructor
public class ResidentAdapter implements ResidentPort {

    private final VisitorJpaRepository visitorJpa;
    private final QrJpaRepository qrJpaRepository;
    private final JwtSecurity jwtSecurity;

    @Override
    public Integer registerVisit(Integer userId, Integer homeId, String homeName, String visitorName, String visitorDocument,
                                 String telephone, boolean favorite, Date expirationDate) {
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromResident(userId, homeId, homeName, visitorName,
            visitorDocument, telephone, favorite));
        generateAndSaveQR(visitor, expirationDate);
        return visitor.getId();
    }

    @Override
    public List<RegisteredVisitor> obtainVisitors(String emailResident) {
        List<VisitorOrm> visitorOrms = visitorJpa.obtainVisitorByEmailResident(emailResident);
        return visitorOrms
            .stream()
            .map(visitorOrm -> {
                QrOrm lastQR = visitorOrm.getQrs().get(visitorOrm.getQrs().size() - 1);
                return new RegisteredVisitor(visitorOrm.getId(), visitorOrm.getName(),
                    visitorOrm.getDocument(), visitorOrm.isFavorite(),
                    lastQR.isAvailableToEnter());
            })
            .toList();
    }

    @Override
    public QrVisitor obtainQRVisitor(Integer idVisitor) {
        QrOrm qrOrm = visitorJpa.obtainQRByIdVisitor(idVisitor);
        if (qrOrm == null) {
            return null;
        }
        return new QrVisitor(qrOrm.getQr(), qrOrm.getVisitor().getName());
    }

    @Override
    public RegisteredVisitor obtainVisitorById(Integer idVisitor) {
        Optional<VisitorOrm> visitorOrm = visitorJpa.findById(idVisitor);
        if (visitorOrm.isPresent() && !visitorOrm.get().isDeleted()) {
            return new RegisteredVisitor(visitorOrm.get().getId(), visitorOrm.get().getName(),
                visitorOrm.get().getDocument(), visitorOrm.get().isFavorite(), true);
        }
        return null;
    }


    @Override
    public String reactiveVisitor(Integer idVisitor, Date expirationDate) {
        Optional<VisitorOrm> opt = visitorJpa.findById(idVisitor);
        if (opt.isPresent()) {
            VisitorOrm visitorOrm = opt.get();
            return generateAndSaveQR(visitorOrm, expirationDate);
        }
        return null;
    }

    @Override
    public void deactivateVisitor(Integer idVisitor) {
        visitorJpa.deleteVisitorById(idVisitor);
    }

    private String generateAndSaveQR(VisitorOrm visitor, Date expirationDate) {
        String token = generateToken(visitor.getDocument(), visitor.toString(), expirationDate);
        QrOrm qr = new QrOrm();
        qr.setVisitor(visitor);
        qr.setAvailable(true);
        qr.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        qr.setQr(token);
        qrJpaRepository.save(qr);
        return token;
    }

    private String generateToken(String documentId, String info, Date expirationDate) {
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("info", info);
        return jwtSecurity.generateToken(documentId, claims, now, expirationDate);
    }
}
