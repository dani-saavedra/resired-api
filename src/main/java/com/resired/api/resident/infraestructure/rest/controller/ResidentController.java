package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.application.usecase.VisitUseCase;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
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
        String qr = visitUseCase.createVisitor(userApp.email(), visitor);
        return new ResponseData<>(qr);
    }

    @GetMapping(path = "/visitors")
    @Operation(summary = "Obtain visitors by resident")
    public List<RegisteredVisitor> obtainVisitor(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return visitUseCase.obtainVisitorByResident(userApp.email());
    }

    @PutMapping(path = "/visitor/{document}/enable")
    @Operation(summary = "Enable a visitor to enter again")
    public ResponseData<String> allowVisitorToEnter(@RequestHeader(value = "Authorization") String bearer,
                                                    @PathVariable String document) {
        UserApp userApp = jwtService.extractUser(bearer);
        String qr = visitUseCase.allowVisitorToEnterAgain(userApp.email(), document);
        return new ResponseData<>(qr);
    }

    @DeleteMapping(path = "/visitor/{document}")
    @Operation(summary = "Delete a visitor registered by a resident")
    public ResponseData<String> deleteVisitor(@RequestHeader(value = "Authorization") String bearer,
                              @PathVariable(value = "document") String documentVisitor) {
        UserApp userApp = jwtService.extractUser(bearer);
        visitUseCase.removeVisitor(userApp.userId(), documentVisitor);
        return new ResponseData<>("Successfully deleted visitor");
    }
}
