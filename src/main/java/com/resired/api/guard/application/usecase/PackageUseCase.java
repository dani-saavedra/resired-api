package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.guard.application.exception.PackageNotFoundException;
import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.exception.ResidentNotFoundOnHomeException;
import com.resired.api.guard.domain.repository.PackagePort;
import com.resired.api.resident.domain.enums.PackageStatusEnum;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PackageUseCase {

    private final UserPort userPort;
    private final PackagePort packagePort;
    private final PushAppUseCase pushAppUseCase;

    public void registerPackage(String emailGuard, PackageRequestDTO packageRequestDTO) {
        User guard = validateGuard(emailGuard);
        Package packet = Package.createNewPackage(
            guard.getId(),
            packageRequestDTO.homeId(),
            packageRequestDTO.receiver(),
            packageRequestDTO.trackingNumber(),
            packageRequestDTO.packageTransporter(),
            packageRequestDTO.description()
        );

        packagePort.registerPackage(packet);
        NotificationHomeRequest notification = new NotificationHomeRequest("Paquete en porteria",
            "Ha llegado un paquete de " + packageRequestDTO.packageTransporter() + " para " + packageRequestDTO.receiver(),
            packageRequestDTO.homeId());
        pushAppUseCase.notifyHome(notification);
    }

    public List<Package> getPackagesByNeighborhood(String emailGuard, Integer neighborhoodId) {
        validateGuard(emailGuard);
        return packagePort.findAllByNeighborhoodId(neighborhoodId);
    }

    public List<Package> getPackagesByHome(Integer homeId) {
        return packagePort.findByHome(homeId);
    }

    public List<Package> getPackagesByStatus(Integer neighborhoodId, PackageStatusEnum status) {
        return packagePort.findPackagesByStatus(neighborhoodId, status.name());
    }

    public void deliverPackage(Integer packageId, Integer neighborhoodId,
                               String lastFourDigits, Integer deliveredGuardId) {

        Package packageToDeliver = packagePort.findPackageByIdAndByNeighborhoodId(packageId, neighborhoodId);
        if (packageToDeliver == null) {
            throw new PackageNotFoundException(packageId, neighborhoodId);
        }
        validateLastFourDigits(lastFourDigits, packageToDeliver.getHomeId());
        packagePort.updatePackage(packageToDeliver);
    }

    private User validateGuard(String emailGuard) {
        User guard = userPort.getGuardByEmail(emailGuard);
        if (guard == null || !guard.isActive()) {
            throw new InactiveUserException(emailGuard);
        }
        return guard;
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
