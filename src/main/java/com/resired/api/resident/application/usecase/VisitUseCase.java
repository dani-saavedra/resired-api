package com.resired.api.resident.application.usecase;

import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.application.exception.InvalidHomeException;
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
public class VisitUseCase {

    private final UserPort userPort;
    private final HomePort homePort;
    private final ResidentPort residentPort;

    public String createVisitor(String documentResident, VisitorRequestDTO visitor) {
        User resident = userPort.getResidentByDocument(documentResident);
        if (resident == null || !resident.isActive()) {
            throw new InactiveUserException(documentResident);
        }
        Home home = homePort.getHomeById(visitor.homeId());
        if (home == null) {
            throw new InvalidHomeException(documentResident);
        }
        return residentPort.registerVisit(resident.getId(), home.getId(), home.getName(),
            visitor.name(), visitor.documentId(), visitor.telephone());
        //TODO Creacion de QR para enviarlo por wp
    }

    public void validateInfoQR() {
        //TODO Obtener data del QR desde jwt
    }


    public void enableQrAgain() {

    }

    public void removeVisitor() {

    }
}
