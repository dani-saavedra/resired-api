package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
@AllArgsConstructor
public class QrAdapter implements QrPort {

    private final QrJpaRepository qrJpaRepository;

    @Override
    public Visitor obtainInfoQR(String qrStr) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);
        return Visitor.createVisitor(qr.getVisitor().getName(),
            qr.getVisitor().getDocument(), qr.getVisitor().getAuthorizingHome().getNumber(),
            qr.isAvailable(), qr.getVisitor().isFavorite(), qr.getVisitor().isDeleted());
    }

    @Override
    public boolean isAvailableQR(String qr) {
        QrOrm qrOrm = qrJpaRepository.findByQr(qr);
        return (qrOrm.isAvailable() || qrOrm.getVisitor().isFavorite()) && !qrOrm.getVisitor().isDeleted();
    }

    @Override
    public void disableVisitorQrByIdVisitor(Integer idVisitor) {
        qrJpaRepository.disableVisitorQrByIdVisitor(LocalDateTime.now(ZoneOffset.UTC), idVisitor);
    }
}
