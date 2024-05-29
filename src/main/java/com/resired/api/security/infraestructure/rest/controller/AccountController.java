package com.resired.api.security.infraestructure.rest.controller;


import com.resired.api.security.application.dto.ResetPasswordRequest;
import com.resired.api.security.application.usecase.AccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/recovery")
    public ResponseEntity<String> processForgotPasswordForm(@RequestParam("email") String email) {
        useCase.createPasswordResetTokenForUser(email);
        return ResponseEntity.ok("Email send");
    }
}
