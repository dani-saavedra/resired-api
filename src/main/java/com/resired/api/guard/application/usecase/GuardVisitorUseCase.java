package com.resired.api.guard.application.usecase;

import com.resired.api.guard.application.dto.ActiveVisitorDto;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.guard.domain.service.VisitorGuardService;
import com.resired.api.guard.domain.vo.VisitVO;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GuardVisitorUseCase {

    private final GuardPort guardPort;
    private final VisitorGuardService visitorGuardService;


    public void registerVisitor(String emailGuard, VisitorRequestDTO visitorDto, Integer guardId) {
        VisitVO residentVisit = new VisitVO(visitorDto.name(), visitorDto.documentId(), visitorDto.telephone(),
            visitorDto.homeId(), emailGuard, false);
        String tokenUUID = visitorGuardService.createVisitor(residentVisit);
        guardPort.registerVisit(tokenUUID, guardId);
    }

    public List<ActiveVisitorDto> getActiveQrVisitors(Integer neighborhoodId) {
        return guardPort.findAllVisitorsWithActiveQr(neighborhoodId).stream()
            .map(visitor -> new ActiveVisitorDto(
                visitor.getVisitorId(),
                visitor.getVisitorName(),
                visitor.getVisitorDocument(),
                visitor.getHome()
            )).toList();
    }
}
