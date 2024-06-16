package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.domain.entity.Resident;
import com.resired.api.admin.application.usecase.AdminResidentUseCase;
import com.resired.api.admin.application.usecase.AdminUserUseCase;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.admin.infraestructure.rest.dto.InfoUserRequest;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.security.domain.enums.UserType;
import io.swagger.v3.oas.annotations.Operation;
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
public class AdmResidentController {

    private final JwtService jwtService;
    private final AdminUserUseCase userUseCase;
    private final AdminResidentUseCase adminResidentUseCase;

    @PostMapping(path = "/resident")
    @Operation(summary = "Register/associate resident to an apartment")
    public ResponseEntity<String> registerResident(@RequestHeader(value = "Authorization") String bearer,
                                                   @RequestBody InfoUserRequest request) throws GeneralSecurityException {
        UserApp userApp = jwtService.extractUser(bearer);
        RegisterUserVO registerUserVO = new RegisterUserVO(request.documentId(), request.documentType(),
            request.firstName(), request.lastName(), request.email(), userApp.neighborhoodId(), request.homeId(), UserType.RESIDENT);

        userUseCase.registerUserToNeighborhood(registerUserVO, userApp.email());
        return ResponseEntity.ok("Resident registered successfully");
    }

    @DeleteMapping(path = "/home/{id_home}/residents")
    @Operation(summary = "Remove all resident registered to a home")
    public ResponseEntity<String> removeHomeResidents(@RequestHeader(value = "Authorization") String bearer,
                                                      @PathVariable(value = "id_home") Integer idHome) {
        UserApp userApp = jwtService.extractUser(bearer);

        adminResidentUseCase.removeResidentsByHome(userApp.neighborhoodId(), idHome);
        return ResponseEntity.ok("Residents removed successfully");
    }

    @DeleteMapping(path = "/residents/{id_user}")
    @Operation(summary = "Remove a registered resident from neighborhood")
    public ResponseEntity<String> removeHomeResident(@RequestHeader(value = "Authorization") String bearer,
                                                     @PathVariable(value = "id_user") Integer idUser) {
        UserApp userApp = jwtService.extractUser(bearer);

        userUseCase.removeResidentByUserId(userApp.neighborhoodId(), idUser);
        return ResponseEntity.ok("Resident removed successfully");
    }

    @GetMapping(path = "/neighborhood/residents")
    @Operation(summary = "Obtain residents of a neighborhood")
    public List<Resident> getResidentsByNeighborhood(@RequestHeader(value = "Authorization") String bearer,
                                                     @RequestParam(required = false, defaultValue = "true") boolean active) {
        UserApp userApp = jwtService.extractUser(bearer);
        return userUseCase.getResidentByNeighborhood(userApp.neighborhoodId(), active);
    }

    @GetMapping(path = "/home/{homeId}/residents")
    @Operation(summary = "Obtain residents of a home")
    public List<Resident> getResidentsByHome(@PathVariable Integer homeId) {
        return userUseCase.getResidentByHome(homeId);
    }
}
