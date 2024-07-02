package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.usecase.AdminPqrsUseCase;
import com.resired.api.admin.application.vo.ResponsePQRS;
import com.resired.api.admin.infraestructure.rest.dto.PqrsResponseAdminDTO;
import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/pqrs")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmPqrsController {

    private final AdminPqrsUseCase adminPqrsUseCase;
    private final JwtService jwtService;

    @PutMapping("/take")
    @Operation(summary = "Take pqrs from the administrator to start working on it")
    public ResponseData<String> updateState(@RequestHeader(value = "Authorization") String bearer,
                                            @RequestParam(value = "ticket_number") String ticketNumber) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminPqrsUseCase.updateStatePQRS(ticketNumber, userApp.neighborhoodId());
        return new ResponseData<>("Update PQRS successfully");
    }

    @PostMapping("/response")
    @Operation(summary = "Receive a response from the administrator to a query")
    public ResponseData<String> responsePQRS(@RequestHeader(value = "Authorization") String bearer,
                                             @RequestBody PqrsResponseAdminDTO dto) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminPqrsUseCase.responsePQRS(new ResponsePQRS(dto.ticketNumber(), dto.response(), userApp.userId(), userApp.neighborhoodId()));
        return new ResponseData<>("Save PQRS response successfully");
    }

    @GetMapping
    @Operation(summary = "list of PQRS registered for the neighborhood")
    public List<PqrsDetailDTO> responsePQRS(@RequestHeader(value = "Authorization") String bearer,
                                            @RequestParam StatePQRS state) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminPqrsUseCase.obtainPqrsByStateAndNeighborhood(state, userApp.neighborhoodId());
    }
}
