package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.VisitReportDto;
import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.repository.GuardPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminReportUseCase {

    private final GuardPort guardPort;

    public List<VisitReportDto> getVisitsReport(Integer neighborhoodId, LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(LocalTime.MAX);

        List<Visit> visits = guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);

        return visits.stream()
            .map(visit -> new VisitReportDto(
                visit.getVisitorName(),
                visit.getHomeNumber(),
                visit.getVisitorDocument(),
                visit.getCheckIn().toString(),
                visit.getAuthorizingGuardName()))
            .toList();
    }
}
