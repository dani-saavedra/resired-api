package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.infrastructure.sql.jpa.PackageJpaRepository;
import com.resired.api.guard.infrastructure.sql.jpa.VisitJpaRepository;
import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.VisitorJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GuardAdapter implements GuardPort {

    private final QrJpaRepository qrJpaRepository;
    private final VisitJpaRepository visitJpaRepository;
    private final VisitorJpaRepository visitorJpa;
    private final PackageJpaRepository packageJpaRepository;

    @Override
    public void registerVisit(String qrStr) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);

        VisitOrm visit = new VisitOrm();
        visit.setQr(qr);
        visit.setCheckIn(LocalDateTime.now());
        visitJpaRepository.save(visit);
    }

    @Override
    public String registerVisitFromGuard(Integer guardId, Integer homeId, String homeName, String visitorName, String visitorDocument, String visitorTelephone) {
        String tokenUUID = UUID.randomUUID().toString();
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromGuard(guardId, homeId, homeName, visitorName, visitorDocument, visitorTelephone));
        QrOrm qr = new QrOrm();
        qr.setVisitor(visitor);
        qr.setAvailable(false);
        qr.setCreatedAt(LocalDateTime.now());
        qr.setDisabledAt(LocalDateTime.now());
        qr.setQr(tokenUUID);
        qrJpaRepository.save(qr);
        return tokenUUID;
    }

    @Override
    public void registerPackage(Package packet) {
        PackageOrm packageOrm = new PackageOrm();
        packageOrm.setGuardId(packet.getGuardId());
        packageOrm.setHome(packet.getHomeId());
        packageOrm.setReceiver(packet.getReceiver());
        packageOrm.setTrackingNumber(packet.getTrackingNumber());
        packageOrm.setPackageTransporter(packet.getPackageTransporter());
        packageOrm.setDescription(packet.getDescription());
        packageOrm.setCreatedDate(packet.getCreatedDate());
        packageOrm.setStatus(packet.getStatus());

        packageJpaRepository.save(packageOrm);
    }
}
