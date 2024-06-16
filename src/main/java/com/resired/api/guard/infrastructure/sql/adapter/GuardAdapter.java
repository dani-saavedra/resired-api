package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.repository.PackagePort;
import com.resired.api.guard.infrastructure.sql.dto.VisitorWithQrDto;
import com.resired.api.guard.infrastructure.sql.jpa.PackageJpaRepository;
import com.resired.api.guard.infrastructure.sql.jpa.VisitJpaRepository;
import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import com.resired.api.resident.infraestructure.sql.jpa.QrJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.VisitorJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    public void registerVisit(String qrStr, Integer guardId) {
        QrOrm qr = qrJpaRepository.findByQr(qrStr);

        VisitOrm visit = new VisitOrm();
        visit.setQr(qr);
        visit.setCheckIn(LocalDateTime.now(ZoneOffset.UTC));
        UserOrm guard = new UserOrm();
        guard.setId(guardId);
        visit.setAuthorizingGuard(guard);
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
        qr.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        qr.setDisabledAt(LocalDateTime.now(ZoneOffset.UTC));
        qr.setQr(tokenUUID);
        qrJpaRepository.save(qr);
        return tokenUUID;
    }

    @Override
    public List<Visit> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                              LocalDateTime startDate,
                                                              LocalDateTime endDate) {
        List<VisitOrm> visitOrms = visitJpaRepository.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId,
            startDate, endDate);
        return visitOrms.stream().map(this::toVisitDomain).toList();
    }

    @Override
    public List<Visitor> findAllVisitorsWithActiveQr(Integer neighborhoodId) {
        return visitorJpa.findAllWithActiveQr(neighborhoodId).stream()
            .map(this::toVisitorDomain)
            .toList();
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
    public List<Package> findAllByNeighborhoodIdAndStartDate(Integer neighborhoodId, LocalDateTime date) {
        List<PackageOrm> packageOrms = packageJpaRepository.findAllByNeighborhoodIdAndStartDate(neighborhoodId, date);
        return packageOrms.stream().map(this::toPackageDomain).toList();
    }

    @Override
    public Package findPackageByIdAndByNeighborhoodId(Integer packageId, Integer neighborhoodId) {
        PackageOrm packetOrm = packageJpaRepository.findByIdAndNeighborhoodId(packageId, neighborhoodId);
        if (packetOrm != null) {
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

    private Visit toVisitDomain(VisitOrm visitOrm) {
        String guardFullName = visitOrm.getAuthorizingGuard().toEntity().getFullNameLastOneFirst();
        String destination = visitOrm.getQr().getVisitor().getAuthorizingHome().toBasicInfoHome().getFullHomeName();

        return new Visit(
            visitOrm.getId(),
            visitOrm.getQr().getVisitor().getName(),
            visitOrm.getQr().getVisitor().getDocument(),
            destination,
            visitOrm.getCheckIn(),
            guardFullName
        );
    }

    private Visitor toVisitorDomain(VisitorWithQrDto visitorWithQr) {
        return new Visitor(
            visitorWithQr.getVisitor().getName(),
            visitorWithQr.getVisitor().getDocument(),
            visitorWithQr.getVisitor().getAuthorizingHome().toBasicInfoHome().getFullHomeName(),
            visitorWithQr.getQr().getQr()
        );
    }
}
