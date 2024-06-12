package com.resired.api.resident.application.usecase;

import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.resident.domain.service.VisitorResidentService;
import com.resired.api.guard.domain.vo.VisitVO;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitUseCase {

    private final Visitor visitor;
    private final VisitorResidentService visitorResidentService;

    public String createVisitor(String emailResident, Integer homeId, VisitorRequestDTO visitorDto) {
        VisitVO residentVisit = new VisitVO(visitorDto.name(), visitorDto.documentId(), visitorDto.telephone(),
            homeId, emailResident, visitorDto.favorite());
        return visitor.createVisit(residentVisit, visitorResidentService);
    }

    public List<RegisteredVisitor> obtainVisitorsByResident(String emailResident) {
        return visitorResidentService.obtainVisitors(emailResident);
    }

    public String allowVisitorToEnterAgain(String emailResident, String documentVisitor) {
        return visitorResidentService.allowVisitorToEnterAgain(emailResident, documentVisitor);
    }

    public void removeVisitor(Integer userId, String documentVisitor) {
        visitorResidentService.deleteVisitor(userId, documentVisitor);
    }

    public QrVisitor obtainVisitorByDocument(String emailResident, String documentVisitor) {
        return visitorResidentService.obtainVisitorByDocument(emailResident, documentVisitor);
    }
}
