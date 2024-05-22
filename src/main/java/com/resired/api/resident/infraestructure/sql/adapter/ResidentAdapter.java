package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.VisitorJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
@AllArgsConstructor
public class ResidentAdapter implements ResidentPort {

    private final VisitorJpaRepository visitorJpa;
    private final QrJpaRepository qrJpaRepository;

    private static final String SECRET_KEY = "5E8C1D0E9A3F6B5C9D0A7E6B1A3D9E2A6E2A4C1E6B2A8C2D7D8D2C6D6C2A2E5";
    private static final long EXPIRATION_TIME = 172800000;

    @Override
    public String registerVisit(Integer userId, Integer homeId, String homeName, String vistorName, String visitorDocument, String telephone) {
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromResident(userId, homeId, homeName, vistorName, visitorDocument, telephone));
        String token = generateToken(visitorDocument, visitor.toString());
        QrOrm qr = new QrOrm();
        qr.setVisitor(visitor);
        qr.setAvailable(true);
        qr.setCreatedAt(LocalDateTime.now());
        qr.setQr(token);
        qrJpaRepository.save(qr);
        return token;
    }

    private String generateToken(String documentId, String info) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder().subject(documentId).issuedAt(now)
            .expiration(expiration).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .claim("info", info)
            .compact();
    }
}
