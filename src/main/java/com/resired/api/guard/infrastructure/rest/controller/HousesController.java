package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.admin.application.usecase.AdminHomesUseCase;
import com.resired.api.guard.application.dto.HomeNeighborhoodDto;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/neighborhood")
@AllArgsConstructor
public class HousesController {

    private final JwtService jwtService;
    private final AdminHomesUseCase homesUseCase;

    @GetMapping("/homes")
    @PreAuthorize("hasAuthority('GUARD') or hasAuthority('ADMIN')")
    @Operation(summary = "List of names of houses in the neighborhood")
    public List<HomeNeighborhoodDto> getHomesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return homesUseCase.getNamesHomesByNeighborhood(userApp.neighborhoodId());
    }
}
