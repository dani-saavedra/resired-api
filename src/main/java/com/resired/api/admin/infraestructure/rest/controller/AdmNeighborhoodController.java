package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.CreateNeighborhood;
import com.resired.api.admin.application.usecase.AdminNeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmNeighborhoodController {

    private AdminNeighborhoodUseCase adminNeighborhoodUseCase;

    @PostMapping(path = "/neigborhood")
    public ResponseEntity<String> createNeighborhood(@RequestBody CreateNeighborhood createNeighborhood) {
        adminNeighborhoodUseCase.createNewNeighborhood(createNeighborhood);
        return ResponseEntity.ok("Success");
    }
}
