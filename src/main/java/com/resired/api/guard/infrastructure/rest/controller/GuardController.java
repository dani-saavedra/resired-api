package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.dto.PackageResponseDTO;
import com.resired.api.guard.application.dto.VisitResponseDTO;
import com.resired.api.guard.application.usecase.GuardVisitUseCase;
import com.resired.api.guard.application.usecase.PackageUseCase;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.infrastructure.rest.dto.InfoQrRequest;
import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/guard")
@AllArgsConstructor
@PreAuthorize("hasAuthority('GUARD')")
public class GuardController {

    private final GuardVisitUseCase visitUseCase;
    private final PackageUseCase packageUseCase;
    private final JwtService jwtService;

    @GetMapping("/info-qr")
    @Operation(summary = "Obtain information from QR code")
    public Visitor obtainInfoQr(@RequestBody InfoQrRequest infoQrRequest) {
        return visitUseCase.validateInfoQR(infoQrRequest.qr());
    }

    @PostMapping("/visit")
    @Operation(summary = "Register a new visit using QR code")
    public ResponseData<String> registerVisit(@RequestBody InfoQrRequest infoQrRequest) {
        visitUseCase.registerVisit(infoQrRequest.qr());
        return new ResponseData<>("Registered visit successfully");
    }

    @PostMapping("/visitor")
    @Operation(summary = "Register a new visit by obtaining the visitor's data from the guard")
    public ResponseData<String> registerVisitor(@RequestHeader(value = "Authorization") String bearer,
                                                @RequestBody VisitorRequestDTO visitorRequestDTO) {
        String email = jwtService.extractUsername(bearer.substring(7));
        visitUseCase.registerVisitor(email, visitorRequestDTO);
        return new ResponseData<>("Registered visit successfully");
    }

    @PostMapping("/package")
    @Operation(summary = "Register a new package")
    public ResponseData<String> registerPackage(@RequestHeader(value = "Authorization") String bearer,
                                                @RequestBody PackageRequestDTO packageRequestDTO) {
        String token = bearer.substring(7);
        String email = jwtService.extractUsername(token);
        Integer neighborhoodId = jwtService.extractNeighborhood(token);
        packageUseCase.registerPackage(email, packageRequestDTO, neighborhoodId);
        return new ResponseData<>("Registered package successfully");
    }

    @GetMapping("/package")
    @Operation(summary = "Get all the packages by neighborhood")
    public ResponseData<List<PackageResponseDTO>> getPackagesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        String token = bearer.substring(7);
        String email = jwtService.extractUsername(token);
        Integer neighborhoodId = jwtService.extractNeighborhood(token);
        return new ResponseData<>(packageUseCase.getPackagesByNeighborhood(email, neighborhoodId));
    }

    @GetMapping("/visits")
    @Operation(summary = "Get visits from last 24 hours or visits by date")
    public ResponseData<List<VisitResponseDTO>> getVisits(
        @RequestHeader(value = "Authorization") String bearer,
        @Parameter(description = "Date as a String in format YYYY-MM-DD", schema = @Schema(type = "string", format = "date"))
        @RequestParam(value = "date") Optional<String> date) {
        String token = bearer.substring(7);
        Integer neighborhoodId = jwtService.extractNeighborhood(token);
        List<VisitResponseDTO> visits;

        if (date.isEmpty()) {
            visits = visitUseCase.getVisits(neighborhoodId);
        } else {
            visits = visitUseCase.getVisits(neighborhoodId, LocalDate.parse(date.get()));
        }

        return new ResponseData<>(visits);
    }
}
