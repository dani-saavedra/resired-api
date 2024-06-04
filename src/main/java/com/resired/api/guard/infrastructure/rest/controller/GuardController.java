package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.dto.VisitResponseDTO;
import com.resired.api.guard.application.usecase.GuardVisitUseCase;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.infrastructure.rest.dto.InfoQrRequest;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
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
@RequestMapping(path = "/guard")
@AllArgsConstructor
@PreAuthorize("hasAuthority('GUARD')")
public class GuardController {

    private final GuardVisitUseCase visitUseCase;
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
        UserApp userApp = jwtService.extractUser(bearer);
        visitUseCase.registerVisitor(userApp.email(), visitorRequestDTO);
        return new ResponseData<>("Registered visit successfully");
    }

    @GetMapping("/visits")
    @Operation(summary = "Get visits from last 24 hours or visits by date")
    public ResponseData<List<VisitResponseDTO>> getVisits(
        @RequestHeader(value = "Authorization") String bearer,
        @Parameter(description = "Date as a String in format YYYY-MM-DD", schema = @Schema(type = "string", format = "date"))
        @RequestParam(value = "date") Optional<String> date) {
        UserApp userApp = jwtService.extractUser(bearer);
        List<VisitResponseDTO> visits;

        if (date.isEmpty()) {
            visits = visitUseCase.getVisits(userApp.neighborhoodId());
        } else {
            visits = visitUseCase.getVisits(userApp.neighborhoodId(), LocalDate.parse(date.get()));
        }

        return new ResponseData<>(visits);
    }
}
