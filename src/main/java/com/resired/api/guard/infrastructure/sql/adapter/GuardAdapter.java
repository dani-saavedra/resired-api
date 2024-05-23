package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.infrastructure.sql.jpa.VisitJpaRepository;
import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class GuardAdapter implements GuardPort {

    private final QrJpaRepository qrJpaRepository;
    private final VisitJpaRepository visitJpaRepository;

    @Override
    public void registerVisit(String qrStr) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);

        VisitOrm visit = new VisitOrm();
        visit.setQr(qr);
        visit.setCheckIn(LocalDateTime.now());
        visitJpaRepository.save(visit);
    }
}
