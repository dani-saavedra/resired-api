package com.resired.api.guard.domain.service;

import com.resired.api.guard.domain.exception.InvalidRolException;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.vo.Visit;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisitorGuardService implements CreateVisitor {
    private final UserPort userPort;
    private final HomePort homePort;
    private final GuardPort guardPort;

    @Override
    public String createVisitor(Visit homeVisit) {
        User guard = userPort.getGuardByEmail(homeVisit.authorizer());
        if (guard == null || !guard.isActive()) {
            throw new InactiveUserException(homeVisit.authorizer());
        }
        if (!guard.hasRole(UserType.GUARD)) {
            throw new InvalidRolException(guard.getEmail());
        }
        Home home = homePort.getHomeById(homeVisit.homeAuthorizer());
        if (home == null) {
            throw new InvalidHomeException();
        }
        return guardPort.registerVisitFromGuard(guard.getId(), home.getId(), home.getName(),
            homeVisit.nameVisitor(), homeVisit.documentVisitor(), homeVisit.telephoneVisitor());
    }
}
