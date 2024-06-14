package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.usecase.AdminHomesUseCase;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmHomeController {

    private final AdminHomesUseCase adminHomesUseCase;
    private final JwtService jwtService;

    @GetMapping(path = "/homes")
    public List<Home> getHomesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminHomesUseCase.getHomesByNeighborhood(userApp.neighborhoodId());
    }

    @GetMapping(path = "/block/{id}/homes")
    public List<Home> getHomesByBlock(@PathVariable(value = "id") Integer blockId) {
        return adminHomesUseCase.getHomesByBlock(blockId);
    }
}
