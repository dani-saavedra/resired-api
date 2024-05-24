package com.resired.api.resident.application.usecase;

import com.resired.api.guard.domain.service.VisitorService;
import com.resired.api.guard.domain.vo.ResidentVisit;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.security.domain.repository.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisitUseCase {

    private final VisitorService visitorService;

    public String createVisitor(String emailResident, VisitorRequestDTO visitor) {
        ResidentVisit residentVisit = new ResidentVisit(visitor.name(), visitor.documentId(), visitor.telephone(), visitor.homeId(), emailResident);
        return visitorService.createVisitorToResident(residentVisit);
        //TODO Creacion de QR para enviarlo por wp
    }


    public void enableQrAgain() {

    }

    public void removeVisitor() {

    }
}
