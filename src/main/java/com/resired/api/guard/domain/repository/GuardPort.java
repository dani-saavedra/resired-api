package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.entity.Visitor;

import java.time.LocalDateTime;
import java.util.List;

public interface GuardPort {

    void registerVisit(String qr, Integer guardId, String plateNumber);

    String registerVisitFromGuard(Integer userId, Integer homeId, String homeName,
                                  String name, String visitorDocument, String telephone);

    void updateVisitorDocument(String qr, String visitorDocument);

    List<Visit> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                       LocalDateTime startDate,
                                                       LocalDateTime endDate);

    List<Visitor> findAllVisitorsWithActiveQr(Integer neighborhoodId);

}
