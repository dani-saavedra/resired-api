package com.resired.api.guard.domain.service;

import com.resired.api.guard.domain.vo.Visit;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.security.application.exception.InactiveUserException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisitorResident implements ManageVisitor {
    private final UserPort userPort;
    private final HomePort homePort;
    private final ResidentPort residentPort;

    @Override
    public String createVisitor(Visit residentVisit) {
        User resident = userPort.getResidentByEmail(residentVisit.authorizer());
        if (resident == null || !resident.isActive()) {
            throw new InactiveUserException(residentVisit.authorizer());
        }
        Home home = homePort.getHomeById(residentVisit.homeAuthorizer());
        if (home == null) {
            throw new InvalidHomeException();
        }
        return residentPort.registerVisit(resident.getId(), home.getId(), home.getName(),
            residentVisit.nameVisitor(), residentVisit.documentVisitor(), residentVisit.telephoneVisitor());
    }
}
