package com.resired.api.guard.domain.service;

import com.resired.api.guard.domain.entity.Package;
import com.resired.api.guard.domain.repository.GuardPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PackageService {
    private final GuardPort guardPort;

    public void registerPackage(Package packet) {
        guardPort.registerPackage(packet);
    }
}
