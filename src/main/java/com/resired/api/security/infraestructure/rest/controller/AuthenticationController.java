package com.resired.api.security.infraestructure.rest.controller;

import com.resired.api.security.application.dto.AuthenticationRequest;
import com.resired.api.security.application.dto.AuthenticationResponse;
import com.resired.api.security.application.usecase.AuthUseCase;
import java.security.GeneralSecurityException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthUseCase authService;


    @PostMapping(path = "/login")
    @Operation(summary = "Authenticate a user")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest auth) throws GeneralSecurityException {
        return authService.authUser(auth);
    }
}
