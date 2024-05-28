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

    private static final long EXPIRATION_TIME = 172800000;

    @Override
    public String registerVisit(Integer userId, Integer homeId, String homeName, String vistorName, String visitorDocument,
                                String telephone, boolean favorite) {
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromResident(userId, homeId, homeName, vistorName,
            visitorDocument, telephone, favorite));
        return generateAndSaveQR(visitor);
    }

    @Override
    public List<RegisteredVisitor> obtainVisitors(String emailResident) {
        return visitorJpa.obtainVisitorByEmailResident(emailResident)
            .stream()
            .map(visitorOrm -> new RegisteredVisitor(visitorOrm.getId(), visitorOrm.getName(), visitorOrm.getDocument()))
            .toList();
    }

    @Override
    public RegisteredVisitor obtainVisitorByDocumentAndEmailVisitor(String documentVisitor, String emailResident) {
        VisitorOrm visitorOrm = visitorJpa.obtainVisitorByEmailResidentAndDocument(emailResident, documentVisitor);
        if (visitorOrm == null) {
            return null;
        }
        return new RegisteredVisitor(visitorOrm.getId(), visitorOrm.getName(), visitorOrm.getDocument());
    }

    @Override
    public String reactiveVisitor(String emailResident, String visitorDocument) {
        VisitorOrm visitorOrm = visitorJpa.obtainVisitorByEmailResidentAndDocument(emailResident, visitorDocument);
        return generateAndSaveQR(visitorOrm);
    }

    private String generateToken(String documentId, String info) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        Map<String, Object> claims = new HashMap<>();
        claims.put("info", info);
        return jwtSecurity.generateJwt(documentId, claims, expiration, now);
    }

    private String generateAndSaveQR(VisitorOrm visitor) {
        String token = generateToken(visitor.getDocument(), visitor.toString());
        QrOrm qr = new QrOrm();
        qr.setVisitor(visitor);
        qr.setAvailable(true);
        qr.setCreatedAt(LocalDateTime.now());
        qr.setQr(token);
        qrJpaRepository.save(qr);
        return token;
    }
}
