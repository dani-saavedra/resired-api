package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.VisitorJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import com.resired.api.security.domain.service.JwtSecurity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class ResidentAdapter implements ResidentPort {

    private final VisitorJpaRepository visitorJpa;
    private final QrJpaRepository qrJpaRepository;
    private final JwtSecurity jwtSecurity;

    @Override
    public String registerVisit(Integer userId, Integer homeId, String homeName, String vistorName, String visitorDocument,
                                String telephone, boolean favorite, Date expirationDate) {
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromResident(userId, homeId, homeName, vistorName,
            visitorDocument, telephone, favorite));
        return generateAndSaveQR(visitor, expirationDate);
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
                    (lastQR.isAvailable() && lastQR.getCreatedAt().plusDays(1).isAfter(LocalDateTime.now())));
            })
            .toList();
    }

    @Override
    public RegisteredVisitor obtainVisitorByDocumentAndEmailVisitor(String documentVisitor, String emailResident) {
        VisitorOrm visitorOrm = visitorJpa.obtainVisitorByEmailResidentAndDocument(emailResident, documentVisitor);
        if (visitorOrm == null) {
            return null;
        }
        return new RegisteredVisitor(visitorOrm.getId(), visitorOrm.getName(),
            visitorOrm.getDocument(), visitorOrm.isFavorite(), true);
    }

    @Override
    public String reactiveVisitor(String emailResident, String visitorDocument, Date expirationDate) {
        VisitorOrm visitorOrm = visitorJpa.obtainVisitorByEmailResidentAndDocument(emailResident, visitorDocument);
        return generateAndSaveQR(visitorOrm, expirationDate);
    }

    @Override
    public void deactivateVisitor(Integer userId, String visitorDocument) {
        visitorJpa.deleteVisitorByUserId(userId, visitorDocument);
    }

    private String generateAndSaveQR(VisitorOrm visitor, Date expirationDate) {
        String token = generateToken(visitor.getDocument(), visitor.toString(), expirationDate);
        QrOrm qr = new QrOrm();
        qr.setVisitor(visitor);
        qr.setAvailable(true);
        qr.setCreatedAt(LocalDateTime.now());
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
