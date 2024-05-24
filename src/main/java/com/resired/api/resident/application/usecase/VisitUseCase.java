package com.resired.api.resident.application.usecase;

import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.service.VisitorResident;
import com.resired.api.guard.domain.vo.Visit;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisitUseCase {

    private final Visitor visitor;
    private final VisitorResident visitorResident;

    public String createVisitor(String emailResident, VisitorRequestDTO visitorDto) {
        Visit residentVisit = new Visit(visitorDto.name(), visitorDto.documentId(), visitorDto.telephone(), visitorDto.homeId(), emailResident);
        return visitor.createVisit(residentVisit, visitorResident);
    }


    public void enableQrAgain() {

    }

    public void removeVisitor() {

    }
}
