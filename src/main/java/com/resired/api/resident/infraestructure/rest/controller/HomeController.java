package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.guard.application.usecase.PackageUseCase;
import com.resired.api.guard.domain.entity.Package;
import com.resired.api.resident.application.dto.PackagesResponse;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/home")
@AllArgsConstructor
@PreAuthorize("hasAuthority('RESIDENT')")
public class HomeController {

    private final PackageUseCase packageUseCase;

    private final JwtService jwtService;

    @GetMapping("/packages")
    @Operation(summary = "Get all packages for the resident's home")
    public PackagesResponse getPackages(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        List<Package> packages = packageUseCase.getPackagesByHome(userApp.homeId());
        return new PackagesResponse(packages);
    }
}
