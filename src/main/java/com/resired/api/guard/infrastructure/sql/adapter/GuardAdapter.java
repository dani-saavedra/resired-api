package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.repository.PackagePort;
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
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GuardAdapter implements GuardPort, PackagePort {

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
    public String registerVisitFromGuard(Integer guardId, Integer homeId, String homeName,
                                         String visitorName, String visitorDocument, String visitorTelephone) {
        String tokenUUID = UUID.randomUUID().toString();
        VisitorOrm visitor = visitorJpa.save(VisitorOrm.visitorFromGuard(guardId, homeId, homeName,
            visitorName, visitorDocument, visitorTelephone));
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
    public List<Visit> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                              LocalDateTime startDate,
                                                              LocalDateTime endDate) {
        return visitJpaRepository.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);
    }

    @Override
    public void registerPackage(Package packet) {
        PackageOrm packageOrm = new PackageOrm();
        packageOrm.setGuardReceivedId(packet.getReceivedGuardId());
        packageOrm.setHome(packet.getHomeId());
        packageOrm.setReceiver(packet.getReceiver());
        packageOrm.setTrackingNumber(packet.getTrackingNumber());
        packageOrm.setPackageTransporter(packet.getPackageTransporter());
        packageOrm.setDescription(packet.getDescription());
        packageOrm.setCreatedDate(packet.getCreatedDate());
        packageOrm.setStatus(packet.getStatus());

        packageJpaRepository.save(packageOrm);
    }

    @Override
    public List<Package> findAllByNeighborhoodId(Integer neighborhoodId) {
        List<PackageOrm> packageOrms = packageJpaRepository.findAllByNeighborhoodId(neighborhoodId);
        return packageOrms.stream().map(this::toPackageDomain).toList();
    }

    @Override
    public Package findPackageByIdAndByNeighborhoodId(Integer packageId, Integer neighborhoodId) {
        PackageOrm packetOrm = packageJpaRepository.findByIdAndNeighborhoodId(packageId, neighborhoodId);
        if(packetOrm != null){
            return toPackageDomain(packetOrm);
        }
        return null;
    }

    @Override
    public void updatePackage(Package packet) {
        packageJpaRepository.save(fromEntity(packet));
    }

    private Package toPackageDomain(PackageOrm packageOrm) {
        return Package.fromExistingPackage(
            packageOrm.getId(),
            packageOrm.getGuardReceivedId(),
            packageOrm.getHome(),
            packageOrm.getReceiver(),
            packageOrm.getTrackingNumber(),
            packageOrm.getPackageTransporter(),
            packageOrm.getDescription(),
            packageOrm.getStatus(),
            packageOrm.getCreatedDate(),
            packageOrm.getUpdateDate(),
            packageOrm.getDeliveredGuardId(),
            packageOrm.getReceiverLastFourDigits()
        );
    }

    public PackageOrm fromEntity(Package packet) {
        return new PackageOrm(
            packet.getId(),
            packet.getReceivedGuardId(),
            packet.getHomeId(),
            packet.getReceiver(),
            packet.getTrackingNumber(),
            packet.getPackageTransporter(),
            packet.getDescription(),
            packet.getStatus(),
            packet.getCreatedDate(),
            packet.getUpdateDate(),
            packet.getDeliveredGuardId(),
            packet.getReceiverLastFourDigits()
        );
    }
}
