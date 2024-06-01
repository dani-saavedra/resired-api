package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.PackagesResponse;
import com.resired.api.resident.application.usecase.HomeUseCase;
import com.resired.api.security.application.usecase.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/home")
@AllArgsConstructor
@PreAuthorize("hasAuthority('RESIDENT')")
public class HomeController {

    private final HomeUseCase useCase;

    private final JwtService jwtService;

    @GetMapping("/packages")
    public PackagesResponse getPackages(@RequestHeader(value = "Authorization") String bearer) {
        String token = bearer.substring(7);
        Integer homeId = jwtService.extractHome(token);
        return useCase.getPackages(homeId);
    }
}
