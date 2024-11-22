package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.application.usecase.PqrsUseCase;
import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.infraestructure.rest.dto.PqrsRequestDTO;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/resident/pqrs")
@AllArgsConstructor
@PreAuthorize("hasAuthority('RESIDENT')")
public class PqrController {
    private final PqrsUseCase useCase;
    private final JwtService jwtService;

    @GetMapping(path = "/all")
    @Operation(summary = "Obtain PQRS registered by the user")
    public List<PqrsResponseDTO> obtainPQRSByResident(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return useCase.obtainPQRSByHome(userApp.homeId());
    }

    @PostMapping("/v2")
    @Operation(summary = "Register PQRS per resident ")
    public ResponseData<String> registerPqrsv2(@RequestHeader(value = "Authorization") String bearer,
                                               @RequestParam("details") MultipartFile details,
                                               @RequestParam("title") String title,
                                               @RequestParam("description") String description,
                                               @RequestParam("category") String category) throws IOException {
        UserApp userApp = jwtService.extractUser(bearer);
        String ticketNumber = useCase.registerPQRSByResident(new RegisterPqrs(userApp.neighborhoodId(), userApp.userId(),
            title, CategoryPQRS.valueOf(category), description, userApp.homeId(), details));
        return new ResponseData<>(ticketNumber);
    }

    @PostMapping
    @Operation(summary = "Register PQRS per resident ")
    public ResponseData<String> registerPqrs(@RequestHeader(value = "Authorization") String bearer,
                                             @RequestBody PqrsRequestDTO dto) throws IOException {
        UserApp userApp = jwtService.extractUser(bearer);
        String ticketNumber = useCase.registerPQRSByResident(new RegisterPqrs(userApp.neighborhoodId(), userApp.userId(),
            dto.title(), dto.category(), dto.description(), userApp.homeId(), null));
        return new ResponseData<>(ticketNumber);
    }

    @GetMapping(path = "/{ticketNumber}")
    @Operation(summary = "Obtain detail information of PQRS")
    public PqrsDetailDTO obtainDetailInformationPqrs(@PathVariable String ticketNumber) {
        return useCase.obtainDetailInformationPqrs(ticketNumber);
    }
}
