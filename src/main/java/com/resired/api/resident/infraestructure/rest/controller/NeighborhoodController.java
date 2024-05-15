package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.application.usecase.NeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/neighborhood")
@AllArgsConstructor
public class NeighborhoodController {

    private final NeighborhoodUseCase useCase;

    @GetMapping("/{id}/news")
    public NewsResponse getNews(@PathVariable(name = "id") Long neighborhoodId) {
        return useCase.getNewsFromNeighborhood(neighborhoodId);
    }
}
