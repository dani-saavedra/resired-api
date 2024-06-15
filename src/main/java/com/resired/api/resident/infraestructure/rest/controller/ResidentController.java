package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.application.usecase.VisitUseCase;
import com.resired.api.resident.domain.vo.QrVisitor;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/resident/")
@AllArgsConstructor
@PreAuthorize("hasAuthority('RESIDENT')")
public class ResidentController {

    private final VisitUseCase visitUseCase;
    private final JwtService jwtService;

    @PostMapping(path = "/visitor")
    @Operation(summary = "Create a qr for a new visitor")
    public ResponseData<String> createVisitor(@RequestHeader(value = "Authorization") String bearer,
                                              @RequestBody VisitorRequestDTO visitor) {
        UserApp userApp = jwtService.extractUser(bearer);
        String qr = visitUseCase.createVisitor(userApp.email(), userApp.homeId(), visitor);
        return new ResponseData<>(qr);
    }

    @GetMapping(path = "/visitors")
    @Operation(summary = "Obtain visitors by resident")
    public List<RegisteredVisitor> obtainVisitor(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return visitUseCase.obtainVisitorsByResident(userApp.email());
    }

    @PutMapping(path = "/visitor/{id}/enable")
    @Operation(summary = "Enable a visitor to enter again")
    public ResponseData<String> allowVisitorToEnter(@PathVariable(value = "id") Integer idVisitor) {
        String qr = visitUseCase.allowVisitorToEnterAgain(idVisitor);
        return new ResponseData<>(qr);
    }

    @DeleteMapping(path = "/visitor/{id}")
    @Operation(summary = "Delete a visitor registered by a resident")
    public ResponseData<String> deleteVisitor(@PathVariable(value = "id") Integer idVisitor) {
        visitUseCase.removeVisitor(idVisitor);
        return new ResponseData<>("Successfully deleted visitor");
    }

    @GetMapping(path = "/visitors/{id}")
    @Operation(summary = "Obtain QR info by ID visitor")
    public QrVisitor obtainVisitor(@PathVariable(value = "id") Integer idVisitor) {
        return visitUseCase.obtainVisitorById(idVisitor);
    }
}
