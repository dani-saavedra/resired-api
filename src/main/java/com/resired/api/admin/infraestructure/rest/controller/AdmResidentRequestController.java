package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.usecase.RequestResidentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/google")
@AllArgsConstructor
public class AdmResidentRequestController {

    private final RequestResidentUseCase useCase;

    @PostMapping(path = "/resident/request")
    @Operation(summary = "Receive a request from a future user of the app to join resired")
    public void registerResident(@RequestBody ResidentRequestDto request) {
        useCase.registerRequestResident(request);
    }
}
