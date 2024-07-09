package com.resired.api.guard.infrastructure.sql.adapter;

import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.repository.PackagePort;
import com.resired.api.guard.infrastructure.sql.jpa.PackageJpaRepository;
import com.resired.api.resident.domain.enums.PackageStatusEnum;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@AllArgsConstructor
public class PackageAdapter implements PackagePort {

    private final PackageJpaRepository packageJpaRepository;

    @Override
    public void registerPackage(Package packet) {
        PackageOrm packageOrm = new PackageOrm();
        UserOrm userOrm = new UserOrm(packet.getReceivedGuardId());
        packageOrm.setGuardReceived(userOrm);
        HomeOrm home = new HomeOrm();
        home.setId(packet.getHomeId());
        packageOrm.setHome(home);
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
    public List<Package> findByHome(Integer homeId) {
        List<PackageOrm> packageOrms = packageJpaRepository.findByHomeId(homeId);
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
        PackageOrm orm = packageJpaRepository.findById(packet.getId()).get();
        orm.setStatus(PackageStatusEnum.DELIVERED);
        orm.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
        orm.setReceiverLastFourDigits(packet.getReceiverLastFourDigits());
        orm.setDeliveredGuard(new UserOrm(packet.getDeliveredGuardId()));
        packageJpaRepository.save(orm);
    }

    private Package toPackageDomain(PackageOrm packageOrm) {
        return Package.fromExistingPackage(
            packageOrm.getId(),
            packageOrm.guardReceived(),
            packageOrm.getHome().getNumber(),
            packageOrm.getReceiver(),
            packageOrm.getTrackingNumber(),
            packageOrm.getPackageTransporter(),
            packageOrm.getDescription(),
            packageOrm.getStatus(),
            packageOrm.getCreatedDate(),
            packageOrm.getUpdateDate(),
            packageOrm.guardDelivered(),
            packageOrm.getReceiverLastFourDigits(),
            packageOrm.getHome().getBlock().getName()
        );
    }
}
