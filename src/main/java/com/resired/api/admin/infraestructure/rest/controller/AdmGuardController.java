package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.GuardResponseDto;
import com.resired.api.admin.application.usecase.AdminUserUseCase;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.admin.infraestructure.rest.dto.InfoUserRequest;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.security.domain.enums.UserType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmGuardController {

    private final JwtService jwtService;
    private final AdminUserUseCase userUseCase;

    @PostMapping(path = "/guard")
    @Operation(summary = "Register/associate guard to a neighborhood")
    public ResponseEntity<String> registerResident(@RequestHeader(value = "Authorization") String bearer,
                                                   @RequestBody InfoUserRequest request) throws GeneralSecurityException {
        UserApp userApp = jwtService.extractUser(bearer);
        RegisterUserVO registerGuardVO = new RegisterUserVO(request.documentId(), request.documentType(),
            request.firstName(), request.lastName(), request.email(), userApp.neighborhoodId(), null,
            UserType.GUARD, userApp.email());

        userUseCase.registerUserToNeighborhood(registerGuardVO, userApp.email());
        return ResponseEntity.ok("Guard registered successfully");
    }

    @DeleteMapping(path = "/guard/{id_user}")
    @Operation(summary = "Remove a registered guard from neighborhood")
    public ResponseEntity<String> removeHomeResident(@RequestHeader(value = "Authorization") String bearer,
                                                     @PathVariable(value = "id_user") Integer idUser) {
        UserApp userApp = jwtService.extractUser(bearer);

        userUseCase.removeResidentByUserId(userApp.neighborhoodId(), idUser);
        return ResponseEntity.ok("Guard removed successfully");
    }

    @GetMapping(path = "/guards")
    @Operation(summary = "Get all guards in the neighborhood")
    public List<GuardResponseDto> getAllGuards(@RequestHeader(value = "Authorization") String bearer,
                                               @Parameter(description = "Active guards as a String in format true or false")
                                               @RequestParam(value = "active", defaultValue = "true") boolean active) {
        UserApp userApp = jwtService.extractUser(bearer);
        return userUseCase.getAllGuards(userApp.neighborhoodId(), active);
    }
}
