package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.Attachment;
import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.usecase.AdminNeighborhoodUseCase;
import com.resired.api.admin.application.usecase.AdminNewsUseCase;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmNeighborhoodController {

    private final AdminNeighborhoodUseCase adminNeighborhoodUseCase;
    private final AdminNewsUseCase adminNewsUseCase;
    private final NeighborhoodUseCase neighborhoodUseCase;
    private final JwtService jwtService;

    @PostMapping(path = "/create-neighborhood")
    public ResponseEntity<String> createNeighborhood(@RequestBody CreateNeighborhoodVo createNeighborhoodVo) throws GeneralSecurityException {
        adminNeighborhoodUseCase.createNewNeighborhood(createNeighborhoodVo);
        return ResponseEntity.ok("Success");
    }

    @PutMapping(path = "/update-neighborhood")
    @Operation(summary = "Set up a neighborhood after onboarding")
    public ResponseEntity<String> configureNeighborhood(@RequestBody NeighConfig neighConfig) {
        adminNeighborhoodUseCase.configNeighborhood(neighConfig);
        return ResponseEntity.ok("Success");
    }


    @PostMapping(path = "/v2/neighborhood/news")
    @Operation(summary = "Create a news for the neighborhood")
    public ResponseData<String> createNews(@RequestHeader(value = "Authorization") String bearer,
                                           @RequestParam(value = "details", required = false) MultipartFile details,
                                           @RequestParam(value = "image", required = false) MultipartFile image,
                                           @RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("category") String category) throws IOException {
        UserApp userApp = jwtService.extractUser(bearer);
        CreateNewsDto news = new CreateNewsDto(title, content, category,
            new Attachment(image.getOriginalFilename(), image.getInputStream()),
            new Attachment(details.getOriginalFilename(), details.getInputStream()));
        adminNewsUseCase.createNews(news, userApp.neighborhoodId());
        return new ResponseData<>("News created successfully");
    }

    @GetMapping(path = "/neighborhood/news")
    @Operation(summary = "Obtain news by neighborhood")
    public NewsResponse obtainNews(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return neighborhoodUseCase.getNewsFromNeighborhood(userApp.neighborhoodId());
    }
}
