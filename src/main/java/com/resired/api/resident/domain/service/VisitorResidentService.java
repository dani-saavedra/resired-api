package com.resired.api.resident.domain.service;

import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.guard.domain.service.CreateVisitor;
import com.resired.api.guard.domain.service.ManageVisitor;
import com.resired.api.guard.domain.vo.Visit;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.exception.InvalidVisitorException;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.repository.UserPort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class VisitorResidentService implements CreateVisitor, ManageVisitor {
    private final UserPort userPort;
    private final HomePort homePort;
    private final ResidentPort residentPort;
    private final QrPort qrPort;

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
        List<RegisteredVisitor> registeredVisitors = residentPort.obtainVisitors(resident.getEmail());
        for (RegisteredVisitor registeredVisitor : registeredVisitors) {
            if (registeredVisitor.document().equals(residentVisit.documentVisitor())) {
                throw new InvalidVisitorException(residentVisit.documentVisitor());
            }
        }
        return residentPort.registerVisit(resident.getId(), home.getId(), home.getName(), residentVisit.nameVisitor(),
            residentVisit.documentVisitor(), residentVisit.telephoneVisitor(), residentVisit.favorite());
    }

    @Override
    public List<RegisteredVisitor> obtainVisitors(String emailResident) {
        return residentPort.obtainVisitors(emailResident);
    }

    @Override
    public String allowVisitorToEnterAgain(String emailResident, String documentVisitor) {
        RegisteredVisitor registeredVisitor = residentPort.obtainVisitorByDocumentAndEmailVisitor(documentVisitor, emailResident);
        if (registeredVisitor == null) {
            throw new InvalidVisitorException(documentVisitor);
        }
        qrPort.disableVisitorQrByIdVisitor(registeredVisitor.id());
        return residentPort.reactiveVisitor(emailResident, documentVisitor);
    }
}
