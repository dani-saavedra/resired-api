package com.resired.api.resident.application.usecase;

import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.service.VisitorResident;
import com.resired.api.guard.domain.vo.Visit;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.domain.repository.ResidentPort;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitUseCase {

    private final Visitor visitor;
    private final VisitorResident visitorResident;
    private final ResidentPort residentPort;

    public String createVisitor(String emailResident, VisitorRequestDTO visitorDto) {
        Visit residentVisit = new Visit(visitorDto.name(), visitorDto.documentId(), visitorDto.telephone(), visitorDto.homeId(), emailResident);
        return visitor.createVisit(residentVisit, visitorResident);
    }

    public List<RegisteredVisitor> obtainVisitorByResident(String emailResident) {
        return residentPort.obtainVisitors(emailResident);
    }


    public void enableQrAgain() {

    }

    public void removeVisitor() {

    }
}
