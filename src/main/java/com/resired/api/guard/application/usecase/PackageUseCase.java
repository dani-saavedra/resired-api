package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.guard.application.dto.PackageResponseDTO;
import com.resired.api.guard.domain.exception.HomeNotFoundException;
import com.resired.api.guard.domain.exception.InvalidRolException;
import com.resired.api.guard.domain.repository.PackagePort;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.guard.domain.entity.Package;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PackageUseCase {
    private final UserPort userPort;
    private final HomePort homePort;
    private final PackagePort packagePort;

    public void registerPackage(String emailGuard, PackageRequestDTO packageRequestDTO) {
        User guard = validateGuard(emailGuard);
        Integer homeId = findHomeId(packageRequestDTO);

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
        User guard = validateGuard(emailGuard);
        List<Package> packages = packagePort.findAllByNeighborhoodId(neighborhoodId);

        return packages.stream()
            .map(pkg -> toPackageResponseDTO(pkg, guard))
            .collect(Collectors.toList());
    }

    private User validateGuard(String emailGuard) {
        User guard = userPort.getGuardByEmail(emailGuard);
        if (guard == null || !guard.isActive()) {
            throw new InactiveUserException(emailGuard);
        }
        if (!guard.hasRole(UserType.GUARD)) {
            throw new InvalidRolException(guard.getEmail());
        }
        return guard;
    }

    private Integer findHomeId(PackageRequestDTO packageRequestDTO) {
        Integer homeId;
        if (packageRequestDTO.block().isPresent()) {
            homeId = homePort.getHomeIdByBlockAndNumber(packageRequestDTO.block().get(),
                packageRequestDTO.homeNumber());
        } else {
            homeId = homePort.getHomeIdByNumber(packageRequestDTO.homeNumber());
        }

        if (homeId == null) {
            throw packageRequestDTO.block()
                .map(block -> new HomeNotFoundException(block, packageRequestDTO.homeNumber()))
                .orElseGet(() -> new HomeNotFoundException(packageRequestDTO.homeNumber()));
        }

        return homeId;
    }

    private PackageResponseDTO toPackageResponseDTO(Package pkg, User guard) {
        String guardName = guard.getUserName() + " " + guard.getUserLastName();
        String[] blockAndHomeNumber = homePort.getBlockAndHomeNumberById(pkg.getHomeId());
        return new PackageResponseDTO(
            guardName,
            blockAndHomeNumber[0],
            blockAndHomeNumber[1],
            pkg.getReceiver(),
            pkg.getTrackingNumber(),
            pkg.getPackageTransporter(),
            pkg.getDescription(),
            pkg.getStatus(),
            pkg.getCreatedDate()
        );
    }
}
