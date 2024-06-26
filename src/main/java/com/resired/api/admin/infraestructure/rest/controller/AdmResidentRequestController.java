package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.usecase.RequestResidentUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdmResidentRequestController {

    private final JwtService jwtService;
    private final RequestResidentUseCase useCase;

    @PostMapping(path = "/google/forms/submit")
    @Operation(summary = "Receive a request from a future user of the app to join resired")
    public void registerResident(@RequestBody ResidentRequestDto request) {
        useCase.registerRequestResident(request);
    }

    @GetMapping(path = "/admin/request/residents")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Obtain list of requests of future user of the app")
    public List<ResidentRequestDto> obtainRequestResident(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return useCase.obtainRequestResident(userApp.neighborhoodId());
    }
}
