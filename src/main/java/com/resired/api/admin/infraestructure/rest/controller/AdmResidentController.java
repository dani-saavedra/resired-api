package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.usecase.ResidentUseCase;
import com.resired.api.admin.domain.vo.RegisterResidentVO;
import com.resired.api.admin.infraestructure.rest.dto.InfoResidentRequest;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmResidentController {

    private final JwtService jwtService;
    private final ResidentUseCase useCase;

    @PostMapping(path = "/resident")
    @Operation(summary = "Register/associate resident to an apartment")
    public ResponseEntity<String> registerResident(@RequestHeader(value = "Authorization") String bearer,
                                                   @RequestBody InfoResidentRequest request) throws GeneralSecurityException {
        UserApp userApp = jwtService.extractUser(bearer);
        RegisterResidentVO registerResidentVO = new RegisterResidentVO(request.documentId(), request.documentType(),
            request.firstName(), request.lastName(), request.email(),
            request.homeId(), userApp.neighborhoodId(), userApp.email());

        useCase.registerResident(registerResidentVO, userApp.email());
        return ResponseEntity.ok("User registered successfully");
    }
}
