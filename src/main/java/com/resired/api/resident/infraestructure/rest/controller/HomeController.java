package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.PackagesResponse;
import com.resired.api.resident.application.usecase.HomeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
@AllArgsConstructor
public class HomeController {

    private final HomeUseCase useCase;

    @GetMapping("/{id}/packages")
    public PackagesResponse getPackages(@PathVariable(name = "id") Long homeId) {
        return useCase.getPackages(homeId);
    }
}
