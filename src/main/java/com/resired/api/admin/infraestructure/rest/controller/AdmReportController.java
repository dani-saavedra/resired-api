package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.VisitReportDto;
import com.resired.api.admin.application.usecase.AdminReportUseCase;
import com.resired.api.guard.application.dto.PackageResponseDTO;
import com.resired.api.guard.application.usecase.PackageUseCase;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmReportController {
    public static final int NUMBER_DAY_ADMIN_QUERY = 30;
    private final JwtService jwtService;
    private final AdminReportUseCase adminReportUseCase;
    private final PackageUseCase packageUseCase;

    @GetMapping("/report/visits")
    @Operation(summary = "Get visits report by date, last five days as default")
    public List<VisitReportDto> getVisitsReport(@RequestHeader(value = "Authorization") String bearer,
                                                @Parameter(description = "Date as a String in format YYYY-MM-DD",
                                                    schema = @Schema(type = "string", format = "date"))
                                                @RequestParam(value = "date") Optional<String> date) {
        UserApp userApp = jwtService.extractUser(bearer);
        List<VisitReportDto> visitsReport;
        if (date.isEmpty()) {
            visitsReport = adminReportUseCase.getVisitsReport(userApp.neighborhoodId());
        } else {
            visitsReport = adminReportUseCase.getVisitsReport(userApp.neighborhoodId(), LocalDate.parse(date.get()));
        }
        return visitsReport;
    }

    @GetMapping("/report/packages")
    @Operation(summary = "Get all the packages from the 30 days per neighborhood")
    public ResponseData<List<PackageResponseDTO>> getPackagesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return new ResponseData<>(packageUseCase.getPackagesByNeighborhood(userApp.email(), userApp.neighborhoodId(), NUMBER_DAY_ADMIN_QUERY));
    }

}
