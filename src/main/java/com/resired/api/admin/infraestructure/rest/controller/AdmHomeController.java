package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.HomeDTO;
import com.resired.api.admin.application.usecase.AdminHomesUseCase;
import com.resired.api.admin.infraestructure.rest.dto.UpdateHomeDTO;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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

    @GetMapping("/download-homes-template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        Resource resource = new ClassPathResource("static/plantilla_residencias.xlsx");

        byte[] fileContent = Files.readAllBytes(resource.getFile().toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "plantilla_residencias.xlsx");

        return ResponseEntity.ok()
            .headers(headers)
            .body(fileContent);
    }
}
