package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.guard.domain.exception.HomeNotFoundException;
import com.resired.api.guard.domain.exception.InvalidRolException;
import com.resired.api.guard.domain.service.PackageService;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.guard.domain.entity.Package;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PackageUseCase {
    private final UserPort userPort;
    private final HomePort homePort;
    private final PackageService packageService;

    public void registerPackage(String emailGuard, PackageRequestDTO packageRequestDTO) {
        User guard = validateGuard(emailGuard);
        Integer homeId = findHomeId(packageRequestDTO);

        Package packet = new Package(
            guard.getId(),
            homeId,
            packageRequestDTO.receiver(),
            packageRequestDTO.trackingNumber(),
            packageRequestDTO.packageTransporter(),
            packageRequestDTO.description()
        );

        packageService.registerPackage(packet);
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
            homeId = homePort.getHomeIdByBlockAndNumber(packageRequestDTO.block().get(), packageRequestDTO.homeNumber());
        } else {
            homeId = homePort.getHomeIdByNumberOnly(packageRequestDTO.homeNumber());
        }

        if (homeId == null) {
            throw packageRequestDTO.block()
                .map(block -> new HomeNotFoundException(block, packageRequestDTO.homeNumber()))
                .orElseGet(() -> new HomeNotFoundException(packageRequestDTO.homeNumber()));
        }

        return homeId;
    }
}
