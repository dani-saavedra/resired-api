package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.Attachment;
import com.resired.api.admin.application.dto.CreateNewsDto;
import com.resired.api.admin.application.usecase.AdminNeighborhoodUseCase;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.NeighConfig;
import com.resired.api.resident.application.dto.NewsResponse;
import com.resired.api.resident.application.usecase.NeighborhoodUseCase;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.dto.RefreshResponse;
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

    @GetMapping(path = "/choose-neighborhood/{neighborhoodId}")
    @Operation(summary = "Choose a neighborhood when admin have several neighborhoods")
    public RefreshResponse chooseNeighborhood(@RequestHeader(value = "Authorization") String bearer,
                                              @PathVariable Integer neighborhoodId) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminNeighborhoodUseCase.chooseNeighborhood(neighborhoodId, userApp.userId());
    }

    @PostMapping(path = "/v1/neighborhood/news")
    @Operation(summary = "Create a news for the neighborhood v1")
    public ResponseData<String> createNewsV1(@RequestHeader(value = "Authorization") String bearer,
                                             @RequestBody CreateNewsDto newsRequest) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminNeighborhoodUseCase.createNewsV1(newsRequest, userApp.neighborhoodId());
        return new ResponseData<>("News created successfully");
    }

    @PostMapping(path = "/v2/neighborhood/news")
    @Operation(summary = "Create a news for the neighborhood v2")
    public ResponseData<String> createNewsV2(@RequestHeader(value = "Authorization") String bearer,
                                             @RequestParam("details") MultipartFile details,
                                             @RequestParam("image") MultipartFile image,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content,
                                             @RequestParam("category") String category) throws IOException {
        UserApp userApp = jwtService.extractUser(bearer);
        CreateNewsDto news = new CreateNewsDto(title, content, category,
            new Attachment(image.getOriginalFilename(), image.getInputStream()),
            new Attachment(details.getOriginalFilename(), details.getInputStream()));
        adminNeighborhoodUseCase.createNewsV2(news, userApp.neighborhoodId());
        return new ResponseData<>("News created successfully");
    }

    @GetMapping(path = "/neighborhood/news")
    @Operation(summary = "Obtain news by neighborhood")
    public NewsResponse obtainNews(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return neighborhoodUseCase.getNewsFromNeighborhood(userApp.neighborhoodId());
    }
}
