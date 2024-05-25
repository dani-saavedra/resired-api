package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class QrAdapter implements QrPort {

    private final QrJpaRepository qrJpaRepository;

    @Override
    public Visitor obtainInfoQR(String qrStr) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);
        UserOrm authorizingUser = qr.getVisitor().getAuthorizingUser();
        String authorizer = "";
        if (authorizingUser != null) {
            authorizer = authorizingUser.getFirstName();
        }
        return Visitor.createVisitor(qr.getVisitor().getName(),
            qr.getVisitor().getDocument(), qr.getVisitor().getAuthorizingHome().getName(),
            authorizer, qr.isAvailable());
    }

    @Override
    public boolean isAvailableQR(String qr) {
        return qrJpaRepository.findByQr(qr).isAvailable();
    }

    @Override
    public void makeQrUnavailable(String qrStr) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);
        qr.setAvailable(false);
        qr.setDisabledAt(LocalDateTime.now());
        qrJpaRepository.save(qr);
    }
}
