package com.resired.api.guard.domain.service;

import com.resired.api.guard.application.exception.InvalidRolException;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.vo.HomeVisit;
import com.resired.api.guard.domain.vo.ResidentVisit;
import com.resired.api.resident.application.exception.InvalidHomeException;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.security.application.exception.InactiveUserException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisitorService {

    private final UserPort userPort;
    private final HomePort homePort;
    private final ResidentPort residentPort;
    private final GuardPort guardPort;

    public String createVisitorToResident(ResidentVisit residentVisit) {
        User resident = userPort.getResidentByEmail(residentVisit.emailAuthorizer());
        if (resident == null || !resident.isActive()) {
            throw new InactiveUserException(residentVisit.emailAuthorizer());
        }
        Home home = homePort.getHomeById(residentVisit.homeAuthorizer());
        if (home == null) {
            throw new InvalidHomeException(residentVisit.emailAuthorizer());
        }
        return residentPort.registerVisit(resident.getId(), home.getId(), home.getName(),
            residentVisit.nameVisitor(), residentVisit.documentVisitor(), residentVisit.telephoneVisitor());
        //TODO Creacion de QR para enviarlo por wp
    }

    public String createVisitorToHome(HomeVisit homeVisit) {
        User guard = userPort.getGuardByEmail(homeVisit.guardAuthorizer());
        if (guard == null || !guard.isActive()) {
            throw new InactiveUserException(homeVisit.guardAuthorizer());
        }
        if (!guard.hasRole(UserType.GUARD)) {
            throw new InvalidRolException(guard.getEmail());
        }
        Home home = homePort.getHomeById(homeVisit.homeAuthorizer());
        if (home == null) {
            throw new InvalidHomeException("" + homeVisit.homeAuthorizer());
        }
        return guardPort.registerVisitFromGuard(guard.getId(), home.getId(), home.getName(),
            homeVisit.nameVisitor(), homeVisit.documentVisitor(), homeVisit.telephoneVisitor());
        //TODO Creacion de QR para enviarlo por wp
    }
}
