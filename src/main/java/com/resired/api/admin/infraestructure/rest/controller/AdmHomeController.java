package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.HomeDTO;
import com.resired.api.admin.infraestructure.rest.dto.UpdateHomeDTO;
import com.resired.api.admin.application.usecase.AdminHomesUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<HomeDTO> getHomesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminHomesUseCase.getHomesByNeighborhood(userApp.neighborhoodId());
    }

    @GetMapping(path = "/block/{id}/homes")
    public List<HomeDTO> getHomesByBlock(@PathVariable(value = "id") Integer blockId) {
        return adminHomesUseCase.getHomesByBlock(blockId);
    }

    @PutMapping(path = "/home")
    public ResponseEntity<String> updateHomeName(@RequestHeader(value = "Authorization") String bearer,
                                                 @RequestBody UpdateHomeDTO updateHomeDTO) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminHomesUseCase.updateHome(userApp.neighborhoodId(), updateHomeDTO.home(), updateHomeDTO.name(),
            updateHomeDTO.meter());
        return ResponseEntity.ok("Updated home");
    }
}
