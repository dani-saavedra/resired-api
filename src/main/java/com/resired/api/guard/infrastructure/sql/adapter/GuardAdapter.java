package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GuardAdapter implements GuardPort {

    private final QrJpaRepository qrJpaRepository;

    @Override
    public Visitor obtainInfoQR(String qrStr) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);
        UserOrm authorizingUser = qr.getVisitor().getAuthorizingUser();
        String authorizer = "";
        if (authorizingUser != null) {
            authorizer = authorizingUser.getFirstName();
        }
        return new Visitor(qr.getVisitor().getName(),
            qr.getVisitor().getDocument(), qr.getVisitor().getAuthorizingHome().getName(),
            authorizer,qr.isAvailable());
    }
}
