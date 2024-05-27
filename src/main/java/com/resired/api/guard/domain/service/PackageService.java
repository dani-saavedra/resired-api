package com.resired.api.guard.domain.service;

import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.guard.domain.exception.InvalidRolException;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PackageService {
    private final UserPort userPort;
    private final HomePort homePort;
    private final GuardPort guardPort;

    public void registerPackage(String emailGuard, String receiver, String trackingNumber, String packageTransporter, String description, String block, String homeNumber) {
        User guard = userPort.getGuardByEmail(emailGuard);
        if (guard == null || !guard.isActive()) {
            throw new InactiveUserException(emailGuard);
        }
        if (!guard.hasRole(UserType.GUARD)) {
            throw new InvalidRolException(guard.getEmail());
        }
        Integer homeId = homePort.getHomeIdByBlockAndNumber(block, homeNumber);
        if (homeId == null) {
            throw new InvalidHomeException();//TODO update exception handler
        }
        guardPort.registerPackage(guard.getId(), homeId, receiver, trackingNumber, packageTransporter, description);
    }
}
