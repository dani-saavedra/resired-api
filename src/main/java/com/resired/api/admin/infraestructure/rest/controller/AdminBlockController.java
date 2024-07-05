package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.usecase.AdminBlocksUseCase;
import com.resired.api.admin.domain.vo.BlockNeighborhood;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminBlockController {
    private final AdminBlocksUseCase adminBlocksUseCase;
    private final JwtService jwtService;

    @GetMapping(path = "/blocks")
    @Operation(summary = "Obtain all blocks by Neighborhood")
    public BlockNeighborhood getBlockByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminBlocksUseCase.getAllBlocksOfNeighborhood(userApp.neighborhoodId());
    }
}
