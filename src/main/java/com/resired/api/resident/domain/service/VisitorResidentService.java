package com.resired.api.resident.domain.service;

import com.resired.api.guard.domain.repository.QrPort;
import com.resired.api.guard.domain.service.CreateVisitor;
import com.resired.api.guard.domain.service.ManageVisitor;
import com.resired.api.guard.domain.vo.VisitVO;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.resident.domain.exception.InvalidVisitorException;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.UserPort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
    public String createVisitor(VisitVO residentVisit) {
        User resident = userPort.getResidentByEmail(residentVisit.authorizer());
        if (resident == null || !resident.isActive()) {
            throw new InactiveUserException(residentVisit.authorizer());
        }
        Home home = homePort.getHomeById(residentVisit.homeAuthorizer());
        if (home == null) {
            throw new InvalidHomeException(residentVisit.homeAuthorizer());
        }
        List<RegisteredVisitor> registeredVisitors = residentPort.obtainVisitors(resident.getEmail());
        for (RegisteredVisitor registeredVisitor : registeredVisitors) {
            if (registeredVisitor.document().equals(residentVisit.documentVisitor())) {
                throw new InvalidVisitorException(registeredVisitor.id());
            }
        }
        Date expirationQr = getQRValidityTime(residentVisit.favorite());
        return residentPort.registerVisit(resident.getId(), home.getId(), home.getName(), residentVisit.nameVisitor(),
            residentVisit.documentVisitor(), residentVisit.telephoneVisitor(), residentVisit.favorite(), expirationQr);
    }

    @Override
    public List<RegisteredVisitor> obtainVisitors(String emailResident) {
        return residentPort.obtainVisitors(emailResident);
    }

    @Override
    public String allowVisitorToEnterAgain(Integer idVisitor) {
        RegisteredVisitor registeredVisitor = residentPort.obtainVisitorById(idVisitor);
        if (registeredVisitor == null) {
            throw new InvalidVisitorException(idVisitor);
        }
        qrPort.disableVisitorQrByIdVisitor(registeredVisitor.id());
        Date expiration = getQRValidityTime(false);
        return residentPort.reactiveVisitor(idVisitor, expiration);
    }

    @Override
    public void deleteVisitor(Integer idVisitor) {
        residentPort.deactivateVisitor(idVisitor);
    }

    @Override
    public QrVisitor obtainVisitorById(Integer idVisitor) {
        return residentPort.obtainQRVisitor(idVisitor);
    }


    private static Date getQRValidityTime(boolean favorite) {
        Date expirationQr;
        Calendar instance = Calendar.getInstance();
        if (favorite) {
            instance.add(Calendar.YEAR, 1);
        } else {
            instance.add(Calendar.DAY_OF_YEAR, 1);
        }
        expirationQr = instance.getTime();
        return expirationQr;
    }
}
