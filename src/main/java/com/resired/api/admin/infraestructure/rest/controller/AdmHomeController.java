package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.HomeDTO;
import com.resired.api.admin.application.usecase.AdminHomesUseCase;
import com.resired.api.admin.infraestructure.exception.InvalidMultipartFileException;
import com.resired.api.admin.infraestructure.rest.dto.NewHomeDTO;
import com.resired.api.admin.infraestructure.rest.dto.UpdateHomeDTO;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmHomeController {

    private final AdminHomesUseCase adminHomesUseCase;
    private final JwtService jwtService;

    @GetMapping(path = "/homes")
    @Operation(summary = "Obtain homes by Neighborhood")
    public List<HomeDTO> getHomesByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminHomesUseCase.getHomesByNeighborhood(userApp.neighborhoodId());
    }

    @GetMapping(path = "/block/{id}/homes")
    @Operation(summary = "Obtain homes by Block")
    public List<HomeDTO> getHomesByBlock(@PathVariable(value = "id") Integer blockId) {
        return adminHomesUseCase.getHomesByBlock(blockId);
    }

    @PutMapping(path = "/home")
    @Operation(summary = "Update information home")
    public ResponseEntity<String> updateHomeName(@RequestHeader(value = "Authorization") String bearer,
                                                 @RequestBody UpdateHomeDTO updateHomeDTO) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminHomesUseCase.updateHome(userApp.neighborhoodId(), updateHomeDTO.home(), updateHomeDTO.name(),
            updateHomeDTO.meter());
        return ResponseEntity.ok("Updated home");
    }

    @PostMapping("/homes/excel")
    @Operation(summary = "Upload a set of homes and blocks from an excel file")
    public ResponseEntity<String> uploadHomesFromExcel(@RequestHeader(value = "Authorization") String bearer,
                                                       @RequestParam("file") MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new InvalidMultipartFileException("MULTIPART_FILE01");
        }

        if (!Objects.equals(file.getContentType(),
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            throw new InvalidMultipartFileException("MULTIPART_FILE02");
        }

        UserApp userApp = jwtService.extractUser(bearer);
        adminHomesUseCase.loadHomesFromExcel(file.getInputStream(), userApp.neighborhoodId());
        return ResponseEntity.ok("Homes added");
    }

    @PostMapping(path = "/home")
    @Operation(summary = "Add residence to neighborhood")
    public ResponseEntity<String> addHome(@RequestHeader(value = "Authorization") String bearer,
                                          @RequestBody NewHomeDTO homeDTO) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminHomesUseCase.addHome(userApp.neighborhoodId(), homeDTO.name(), homeDTO.block(), homeDTO.meter());
        return ResponseEntity.ok("Added home");
    }
}
