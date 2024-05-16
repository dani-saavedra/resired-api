package com.resired.api.security.infraestructure.rest.controller;


import com.resired.api.security.application.dto.ResetPasswordRequest;
import com.resired.api.security.application.usecase.AccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping(path = "/account/")
@AllArgsConstructor
public class AccountController {

    private final AccountUseCase useCase;

    @PutMapping(path = "/password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request) throws GeneralSecurityException {
        useCase.resetPassword(request);
        return ResponseEntity.ok("Password changed successfully");
    }
}
