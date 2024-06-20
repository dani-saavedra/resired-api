package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.dto.ActiveVisitorDto;
import com.resired.api.guard.application.dto.VisitResponseDTO;
import com.resired.api.guard.application.usecase.GuardVisitUseCase;
import com.resired.api.guard.application.usecase.GuardVisitorUseCase;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.infrastructure.rest.dto.InfoQrRequest;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.security.infraestructure.rest.proxy.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final GuardVisitorUseCase visitorUseCase;
    private final JwtService jwtService;

    @GetMapping("/info-qr")
    @Operation(summary = "Obtain information from QR code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK QR"),
        @ApiResponse(responseCode = "409", description = "Problems with QR", content =
        @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public Visitor obtainInfoQr(@RequestParam String qr) {
        return visitUseCase.validateInfoQR(qr);
    }

    @PostMapping("/visit")
    @Operation(summary = "Register a new visit using QR code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registered visit successfully"),
        @ApiResponse(responseCode = "409", description = "Problems with QR", content =
        @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public ResponseData<String> registerVisit(@RequestHeader(value = "Authorization") String bearer,
                                              @RequestBody InfoQrRequest infoQrRequest) {
        UserApp userApp = jwtService.extractUser(bearer);
        visitUseCase.registerVisit(infoQrRequest.qr(), userApp.userId());
        return new ResponseData<>("Registered visit successfully");
    }

    @PostMapping("/visitor")
    @Operation(summary = "Register a new visit by obtaining the visitor's data from the guard")
    public ResponseData<String> registerVisitor(@RequestHeader(value = "Authorization") String bearer,
                                                @RequestBody VisitorRequestDTO visitorRequestDTO) {
        UserApp userApp = jwtService.extractUser(bearer);
        visitorUseCase.registerVisitor(userApp.email(), visitorRequestDTO, userApp.userId());
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

    @GetMapping("/visitors")
    @Operation(summary = "List all visitors on neighborhood with an active QR code")
    public List<ActiveVisitorDto> getActiveQrVisitors(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return visitorUseCase.getActiveQrVisitors(userApp.neighborhoodId());
    }
}
