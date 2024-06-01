package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.application.usecase.NeighborhoodUseCase;
import com.resired.api.security.application.usecase.JwtService;
import lombok.AllArgsConstructor;
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
    public NewsResponse getNews(@RequestHeader(value = "Authorization") String bearer) {
        String token = bearer.substring(7);
        Integer neighborhoodId = jwtService.extractNeighborhood(token);
        return useCase.getNewsFromNeighborhood(neighborhoodId);
    }
}
