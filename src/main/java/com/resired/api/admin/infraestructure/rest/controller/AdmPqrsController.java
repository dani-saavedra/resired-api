package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.usecase.AdminPqrsUseCase;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/pqrs")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmPqrsController {

    private AdminPqrsUseCase adminPqrsUseCase;
    private final JwtService jwtService;

    @PutMapping("/take")
    public ResponseData<String> updateState(@RequestHeader(value = "Authorization") String bearer,
                                            @RequestParam(value = "ticket_number") String ticketNumber) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminPqrsUseCase.updateStatePQRS(ticketNumber, userApp.neighborhoodId());
        return new ResponseData<>("Update PQRS successfully");
    }
}
