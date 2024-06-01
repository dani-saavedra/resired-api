package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.dto.PackageResponseDTO;
import com.resired.api.guard.application.usecase.GuardVisitUseCase;
import com.resired.api.guard.application.usecase.PackageUseCase;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.infrastructure.rest.dto.InfoQrRequest;
import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/guard")
@AllArgsConstructor
@PreAuthorize("hasAuthority('GUARD')")
public class GuardController {

    private final GuardVisitUseCase visitUseCase;
    private final PackageUseCase packageUseCase;
    private final JwtService jwtService;

    @GetMapping("/info-qr")
    public Visitor obtainInfoQr(@RequestBody InfoQrRequest infoQrRequest) {
        return visitUseCase.validateInfoQR(infoQrRequest.qr());
    }

    @PostMapping("/visit")
    public ResponseData<String> registerVisit(@RequestBody InfoQrRequest infoQrRequest) {
        visitUseCase.registerVisit(infoQrRequest.qr());
        return new ResponseData<>("Registered visit successfully");
    }

    @PostMapping("/visitor")
    public ResponseData<String> registerVisitor(@RequestHeader(value = "Authorization") String bearer,
                                                @RequestBody VisitorRequestDTO visitorRequestDTO) {
        String email = jwtService.extractUsername(bearer.substring(7));
        visitUseCase.registerVisitor(email, visitorRequestDTO);
        return new ResponseData<>("Registered visit successfully");
    }

    @PostMapping("/package")
    public ResponseData<String> registerPackage(@RequestHeader(value = "Authorization") String bearer,
                                                @RequestBody PackageRequestDTO packageRequestDTO) {
        String token = bearer.substring(7);
        String email = jwtService.extractUsername(token);
        Integer neighborhoodId = jwtService.extractNeighborhood(token);
        packageUseCase.registerPackage(email, packageRequestDTO, neighborhoodId);
        return new ResponseData<>("Registered package successfully");
    }

    @GetMapping("/package")
    public ResponseData<List<PackageResponseDTO>> getPackagesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        String token = bearer.substring(7);
        String email = jwtService.extractUsername(token);
        Integer neighborhoodId = jwtService.extractNeighborhood(token);
        return new ResponseData<>(packageUseCase.getPackagesByNeighborhood(email, neighborhoodId));
    }
}
