package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.VisitReportDto;
import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.domain.repository.GuardPort;
import com.resired.api.utils.FormatDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminReportUseCase {

    private final GuardPort guardPort;


    public List<VisitReportDto> getVisitsReport(Integer neighborhoodId) {
        LocalDateTime endDate = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startDate = endDate.minusDays(5);
        List<Visit> visits = guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);

        return getVisitsReportDto(visits);
    }

    public List<VisitReportDto> getVisitsReport(Integer neighborhoodId, LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(LocalTime.MAX);
        List<Visit> visits = guardPort.findVisitsByNeighborhoodIdAndDateRange(neighborhoodId, startDate, endDate);

        return getVisitsReportDto(visits);
    }

    private List<VisitReportDto> getVisitsReportDto(List<Visit> visits) {
        return visits.stream()
            .map(visit -> new VisitReportDto(
                visit.getVisitorName(),
                visit.getDestinationHome(),
                visit.getVisitorDocument(),
                FormatDate.formatDate(visit.getCheckIn()),
                visit.getAuthorizingGuardName()))
            .toList();
    }
}
