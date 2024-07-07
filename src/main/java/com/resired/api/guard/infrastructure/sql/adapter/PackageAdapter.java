package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.repository.PackagePort;
import com.resired.api.guard.infrastructure.sql.jpa.PackageJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class PackageAdapter implements PackagePort {

    private final PackageJpaRepository packageJpaRepository;

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
        return packageOrms
            .stream()
            .map(this::toPackageDomain)
            .toList();
    }

    @Override
    public List<Package> findPackagesByStatus(Integer neighborhoodId, String statusEnum) {
        List<PackageOrm> packageOrms = packageJpaRepository.findAllByNeighborhoodIdAndStatus(neighborhoodId, statusEnum);
        return packageOrms
            .stream()
            .map(this::toPackageDomain)
            .toList();
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
}
