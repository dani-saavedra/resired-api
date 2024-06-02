package com.resired.api.security.infraestructure.rest.controller;


import com.resired.api.security.application.dto.RecoveryPasswordRequest;
import com.resired.api.security.application.dto.ResetPasswordRequest;
import com.resired.api.security.application.usecase.AccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Change the account password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request) throws GeneralSecurityException {
        useCase.resetPassword(request);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping(path = "/password/recovery")
    @Operation(summary = "Send an email for the password recovery process")
    public ResponseEntity<String> processForgotPasswordForm(@RequestParam("email") String email) {
        useCase.createPasswordResetTokenForUser(email);
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping(path = "/password/reset")
    @Operation(summary = "Reset the account password with the token sent via email")
    public ResponseEntity<String> processResetPassword(@RequestBody RecoveryPasswordRequest request) throws GeneralSecurityException {
        useCase.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }
}
