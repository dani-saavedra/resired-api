package com.resired.api.resident.application.usecase;

import com.resired.api.guard.domain.vo.VisitVO;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.domain.service.VisitorResidentService;
import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitUseCase {

    private final VisitorResidentService visitorResidentService;

    public Integer createVisitor(String emailResident, Integer homeId, VisitorRequestDTO visitorDto) {
        VisitVO residentVisit = new VisitVO(visitorDto.name(), visitorDto.documentId(), visitorDto.telephone(),
            homeId, emailResident, visitorDto.favorite());
        return visitorResidentService.createVisitor(residentVisit);
    }

    public List<RegisteredVisitor> obtainVisitorsByResident(String emailResident) {
        return visitorResidentService.obtainVisitors(emailResident);
    }

    public String allowVisitorToEnterAgain(Integer idVisitor) {
        return visitorResidentService.allowVisitorToEnterAgain(idVisitor);
    }

    public void removeVisitor(Integer idVisitor) {
        visitorResidentService.deleteVisitor(idVisitor);
    }

    public QrVisitor obtainVisitorById(Integer idVisitor) {
        return visitorResidentService.obtainVisitorById(idVisitor);
    }
}
