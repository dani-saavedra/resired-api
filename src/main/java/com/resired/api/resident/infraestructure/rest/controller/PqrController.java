package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.usecase.PqrsUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/pqrs/resident")
@AllArgsConstructor
@PreAuthorize("hasAuthority('RESIDENT')")
public class PqrController {
    private final PqrsUseCase useCase;
    private final JwtService jwtService;

    @GetMapping(path = "/all")
    @Operation(summary = "Obtain PQRS registered by the user")
    public List<PqrsResponseDTO> obtainPQRSByResident(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);

        return useCase.obtainPQRSByResident(userApp.userId());
    }
}
