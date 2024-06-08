package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.guard.application.dto.PackageResponseDTO;
import com.resired.api.guard.application.exception.PackageNotFoundException;
import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.exception.HomeNotFoundException;
import com.resired.api.guard.domain.exception.ResidentNotFoundOnHomeException;
import com.resired.api.guard.domain.repository.PackagePort;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.utils.FormatDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@AllArgsConstructor
public class PackageUseCase {
    private final UserPort userPort;
    private final HomePort homePort;
    private final PackagePort packagePort;

    public void registerPackage(String emailGuard, PackageRequestDTO packageRequestDTO, Integer neighborhoodId) {
        User guard = validateGuard(emailGuard);
        Integer homeId = findHomeId(packageRequestDTO, neighborhoodId);

        Package packet = Package.createNewPackage(
            guard.getId(),
            homeId,
            packageRequestDTO.receiver(),
            packageRequestDTO.trackingNumber(),
            packageRequestDTO.packageTransporter(),
            packageRequestDTO.description()
        );

        packagePort.registerPackage(packet);
    }

    public List<PackageResponseDTO> getPackagesByNeighborhood(String emailGuard, Integer neighborhoodId) {
        validateGuard(emailGuard);
        LocalDateTime fiveDaysAgo = LocalDateTime.now(ZoneOffset.UTC).minusDays(5);;
        List<Package> packages = packagePort.findAllByNeighborhoodIdAndStartDate(neighborhoodId, fiveDaysAgo);

        return packages.stream()
            .map(this::toPackageResponseDTO)
            .toList();
    }

    public void deliverPackage(Integer packageId, Integer neighborhoodId,
                               String lastFourDigits, Integer deliveredGuardId) {

        Package packageToDeliver = packagePort.findPackageByIdAndByNeighborhoodId(packageId, neighborhoodId);
        if (packageToDeliver == null) {
            throw new PackageNotFoundException(packageId, neighborhoodId);
        }

        validateLastFourDigits(lastFourDigits, packageToDeliver.getHomeId());

        packageToDeliver.deliverPackage(deliveredGuardId, lastFourDigits);
        packagePort.updatePackage(packageToDeliver);
    }

    private User validateGuard(String emailGuard) {
        User guard = userPort.getGuardByEmail(emailGuard);
        if (guard == null || !guard.isActive()) {
            throw new InactiveUserException(emailGuard);
        }
        return guard;
    }

    private Integer findHomeId(PackageRequestDTO packageRequestDTO, Integer neighborhoodId) {
        Integer homeId;
        if (packageRequestDTO.block().isPresent()) {
            homeId = homePort.getHomeIdByBlockAndNumberAndNeighborhoodId(packageRequestDTO.block().get(),
                packageRequestDTO.homeNumber(), neighborhoodId);
        } else {
            homeId = homePort.getHomeIdByNumberAndNeighborhoodId(packageRequestDTO.homeNumber(), neighborhoodId);
        }

        if (homeId == null) {
            throw packageRequestDTO.block()
                .map(block -> new HomeNotFoundException(block, packageRequestDTO.homeNumber()))
                .orElseGet(() -> new HomeNotFoundException(packageRequestDTO.homeNumber()));
        }

        return homeId;
    }

    private PackageResponseDTO toPackageResponseDTO(Package pkg) {
        String homeNumber = homePort.getHomeNumberById(pkg.getHomeId());
        return new PackageResponseDTO(
            pkg.getId(),
            homeNumber,
            pkg.getReceiver(),
            pkg.getTrackingNumber(),
            pkg.getPackageTransporter(),
            pkg.getDescription(),
            pkg.getStatus(),
            FormatDate.formatDate(pkg.getCreatedDate()),
            FormatDate.formatDate(pkg.getUpdateDate())
        );
    }

    private void validateLastFourDigits(String lastFourDigits, Integer homeId) {
        List<User> residents = userPort.findResidentsByHomeId(homeId);
        if (residents.isEmpty()) {
            return;
        }

        boolean isValid = residents.stream()
            .anyMatch(resident -> resident.getDocumentId().endsWith(lastFourDigits));

        if (!isValid) {
            throw new ResidentNotFoundOnHomeException(lastFourDigits, homeId);
        }
    }
}
