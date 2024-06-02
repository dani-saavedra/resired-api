package com.resired.api.guard.domain.repository;

import com.resired.api.guard.application.dto.VisitResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface GuardPort {

    void registerVisit(String qr);

    String registerVisitFromGuard(Integer userId, Integer homeId, String homeName,
                                  String name, String visitorDocument, String telephone);

    List<VisitResponseDTO> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                                  LocalDateTime startDate,
                                                                  LocalDateTime endDate);
}
