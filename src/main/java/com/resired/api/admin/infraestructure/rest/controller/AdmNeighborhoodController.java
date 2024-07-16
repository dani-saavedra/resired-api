package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.usecase.AdminNeighborhoodUseCase;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.NeighConfig;
import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.application.usecase.NeighborhoodUseCase;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmNeighborhoodController {

    private final AdminNeighborhoodUseCase adminNeighborhoodUseCase;
    private final NeighborhoodUseCase neighborhoodUseCase;
    private final JwtService jwtService;

    @PostMapping(path = "/neighborhood")
    public ResponseEntity<String> createNeighborhood(@RequestBody CreateNeighborhoodVo createNeighborhoodVo) throws GeneralSecurityException {
        adminNeighborhoodUseCase.createNewNeighborhood(createNeighborhoodVo);
        return ResponseEntity.ok("Success");
    }

    @PutMapping(path = "/neighborhood")
    @Operation(summary = "Set up a neighborhood after onboarding")
    public ResponseEntity<String> configureNeighborhood(@RequestBody NeighConfig neighConfig) {
        adminNeighborhoodUseCase.configNeighborhood(neighConfig);
        return ResponseEntity.ok("Success");
    }

    @PostMapping(path = "/neighborhood/news")
    @Operation(summary = "Create a news for the neighborhood")
    public ResponseData<String> createNews(@RequestHeader(value = "Authorization") String bearer,
                                           @RequestBody CreateNewsDto newsRequest) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminNeighborhoodUseCase.createNews(newsRequest, userApp.neighborhoodId());
        return new ResponseData<>("News created successfully");
    }

    @GetMapping(path = "/neighborhood/news")
    @Operation(summary = "Obtain news by neighborhood")
    public NewsResponse obtainNews(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return neighborhoodUseCase.getNewsFromNeighborhood(userApp.neighborhoodId());
    }
}
