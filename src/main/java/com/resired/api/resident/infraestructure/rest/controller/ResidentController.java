package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.application.usecase.VisitUseCase;
import com.resired.api.resident.domain.vo.RegisteredVisitor;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import com.resired.api.security.application.usecase.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/resident/")
@AllArgsConstructor
public class ResidentController {

    private final VisitUseCase visitUseCase;
    private final JwtService jwtService;

    @PostMapping(path = "/visitor")
    public ResponseData<String> createVisitor(@RequestHeader(value = "Authorization") String bearer,
                                              @RequestBody VisitorRequestDTO visitor) {
        String email = jwtService.extractUsername(bearer.substring(7));
        String qr = visitUseCase.createVisitor(email, visitor);
        return new ResponseData<>(qr);
    }

    @GetMapping(path = "/visitors")
    public List<RegisteredVisitor> obtainVisitor(@RequestHeader(value = "Authorization") String bearer) {
        String email = jwtService.extractUsername(bearer.substring(7));
        return visitUseCase.obtainVisitorByResident(email);
    }

    @PutMapping(path = "/visitor/{document}/enable")
    public ResponseData<String> allowVisitorToEnter(@RequestHeader(value = "Authorization") String bearer,
                                                    @PathVariable String document) {
        String email = jwtService.extractUsername(bearer.substring(7));
        String qr = visitUseCase.allowVisitorToEnterAgain(email, document);
        return new ResponseData<>(qr);
    }

    @PutMapping(path = "/{documentResident}/visitor/{documentVisitor}")
    public void deleteVisitor(@PathVariable String documentResident, @PathVariable String documentVisitor) {
//
    }

    @PutMapping(path = "/qr/{qrId}")
    public void enableQR(@PathVariable Integer qrId) {
        //revisando con richard el envio
    }

}
