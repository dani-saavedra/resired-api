package com.resired.api.security.infraestructure.rest.controller;

import com.resired.api.security.application.dto.*;
import com.resired.api.security.application.usecase.AuthUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.security.infraestructure.rest.proxy.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping(path = "/auth/")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthUseCase authService;
    private final JwtService jwtService;


    @PostMapping(path = "/login")
    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succesful Authenticacion"),
        @ApiResponse(responseCode = "401", description = "Failed Authentication", content =
        @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest auth) throws GeneralSecurityException {
        return authService.authUser(auth);
    }

    @PostMapping(path = "/admin/login")
    @Operation(summary = "Authenticate administrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succesful Authenticacion"),
        @ApiResponse(responseCode = "401", description = "Failed Authentication", content =
        @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public AuthenticationAdminResponse authenticateAdmin(@RequestBody AuthenticationRequest auth) throws GeneralSecurityException {
        return authService.authAdmin(auth);
    }

    @PostMapping(path = "/admin/refresh-token")
    @Operation(summary = "Refresh Token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful Authenticacion"),
        @ApiResponse(responseCode = "400", description = "Refresh token expired", content =
        @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public RefreshResponse refreshToken(@RequestBody RefreshRequest refreshRequest) {
        return authService.refreshToken(refreshRequest);
    }

    @GetMapping(path = "/admin/choose-neighborhood/{neighborhoodId}")
    @Operation(summary = "Choose a neighborhood when admin have several neighborhoods")
    public RefreshResponse chooseNeighborhood(@RequestHeader(value = "Authorization") String bearer,
                                              @PathVariable Integer neighborhoodId) {
        UserApp userApp = jwtService.extractUser(bearer);
        return authService.chooseNeighborhood(neighborhoodId, userApp.userId());
    }
}
