package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.dto.PackageRequestDTO;
import com.resired.api.guard.application.dto.PackageResponseDTO;
import com.resired.api.guard.application.usecase.PackageUseCase;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/guard")
@AllArgsConstructor
@PreAuthorize("hasAuthority('GUARD')")
public class PackageController {

    private final PackageUseCase packageUseCase;
    private final JwtService jwtService;

    @PostMapping("/package")
    @Operation(summary = "Register a new package")
    public ResponseData<String> registerPackage(@RequestHeader(value = "Authorization") String bearer,
                                                @RequestBody PackageRequestDTO packageRequestDTO) {
        UserApp userApp = jwtService.extractUser(bearer);
        packageUseCase.registerPackage(userApp.email(), packageRequestDTO, userApp.neighborhoodId());
        return new ResponseData<>("Registered package successfully");
    }

    @GetMapping("/package")
    @Operation(summary = "Get all the packages by neighborhood")
    public ResponseData<List<PackageResponseDTO>> getPackagesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return new ResponseData<>(packageUseCase.getPackagesByNeighborhood(userApp.email(), userApp.neighborhoodId()));
    }

    @PutMapping("/package/{packageId}/deliver")
    @Operation(summary = "Deliver a package by verifying the last 4 digits of a resident's document")
    public ResponseData<String> deliverPackage(@RequestHeader(value = "Authorization") String bearer,
                                               @PathVariable Integer packageId,
                                               @Parameter(description = "Last four digit's of a resident document as a String")
                                               @RequestParam String lastFourDigits) {
        UserApp userApp = jwtService.extractUser(bearer);
        packageUseCase.deliverPackage(packageId, userApp.neighborhoodId(), lastFourDigits, userApp.userId());
        return new ResponseData<>("Package delivered successfully");
    }
}
