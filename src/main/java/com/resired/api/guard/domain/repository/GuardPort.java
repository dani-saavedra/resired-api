package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.domain.vo.VisitMade;

import java.time.LocalDateTime;
import java.util.List;

public interface GuardPort {

    VisitMade registerVisit(String qr, Integer guardId, String plateNumber);

    String registerVisitFromGuard(Integer userId, Integer homeId, String homeName,
                                  String name, String visitorDocument, String telephone);

    void updateVisitorDocument(String qr, String visitorDocument);

    List<Visit> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                       LocalDateTime startDate,
                                                       LocalDateTime endDate);

    List<Visit> findVisitsByNeighborhood(Integer neighborhoodId);

    List<Visitor> findAllVisitorsWithActiveQr(Integer neighborhoodId);

}
