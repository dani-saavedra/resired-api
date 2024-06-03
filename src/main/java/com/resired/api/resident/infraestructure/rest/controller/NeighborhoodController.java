package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.application.usecase.NeighborhoodUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/neighborhood")
@AllArgsConstructor
@PreAuthorize("hasAuthority('RESIDENT')")
public class NeighborhoodController {

    private final JwtService jwtService;

    private final NeighborhoodUseCase useCase;

    @GetMapping("/news")
    @Operation(summary = "Get news from the neighborhood")
    public NewsResponse getNews(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return useCase.getNewsFromNeighborhood(userApp.neighborhoodId());
    }
}
