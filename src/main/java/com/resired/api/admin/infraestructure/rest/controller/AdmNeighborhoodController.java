package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.usecase.AdminNeighborhoodUseCase;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
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

    private AdminNeighborhoodUseCase adminNeighborhoodUseCase;
    private JwtService jwtService;

    //TODO revisar rol de owner de resired que realizaria esta creación incial
    @PostMapping(path = "/neigborhood")
    public ResponseEntity<String> createNeighborhood(@RequestBody CreateNeighborhoodVo createNeighborhoodVo) throws GeneralSecurityException {
        adminNeighborhoodUseCase.createNewNeighborhood(createNeighborhoodVo);
        return ResponseEntity.ok("Success");
    }

    @PutMapping(path = "/neigborhood")
    public ResponseEntity<String> configureNeighborhood(@RequestBody CreateNeighborhoodVo createNeighborhoodVo) {
        //adminNeighborhoodUseCase.createNewNeighborhood(createNeighborhood);
        return ResponseEntity.ok("Success");
    }

    @PostMapping(path = "/news")
    public ResponseData<String> createNews(@RequestHeader(value = "Authorization") String bearer,
                                           @RequestBody CreateNewsDto newsRequest) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminNeighborhoodUseCase.createNews(newsRequest, userApp.neighborhoodId());
        return new ResponseData<>("News created successfully");
    }
}
