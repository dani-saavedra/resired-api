package com.resired.api.guard.application.usecase;

import com.resired.api.guard.domain.service.PackageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PackageUseCase {
    private final PackageService packageService;

    public void registerPackage(String emailGuard, String receiver, String trackingNumber, String packageTransporter, String description, Optional<String> block, String homeNumber) {
        packageService.registerPackage(emailGuard, receiver, trackingNumber, packageTransporter, description, block, homeNumber);
    }
}
