package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.usecase.ResidentUseCase;
import com.resired.api.admin.domain.vo.RegisterResidentVO;
import com.resired.api.admin.infraestructure.rest.dto.InfoResidentRequest;
import com.resired.api.security.application.usecase.JwtService;
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
    public ResponseEntity<String> registerResident(@RequestHeader(value = "Authorization") String bearer,
                                                   @RequestBody InfoResidentRequest request) throws GeneralSecurityException {
        String email = jwtService.extractUsername(bearer.substring(7));
        RegisterResidentVO registerResidentVO = new RegisterResidentVO(request.documentId(), request.documentType(),
            request.firstName(), request.lastName(), request.email(), email);
        useCase.registerResident(registerResidentVO, email);
        return ResponseEntity.ok("User registered successfully");
    }
}
