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
    public ResponseData<String> createVisitor(@RequestHeader(value = "Authorization") String bearer, @RequestBody VisitorRequestDTO visitor) {
        String email = jwtService.extractUsername(bearer.substring(7));
        String qr = visitUseCase.createVisitor(email, visitor);
        return new ResponseData<>(qr);
    }

    @GetMapping(path = "/visitors")
    public List<RegisteredVisitor> obtainVisitor(@RequestHeader(value = "Authorization") String bearer) {
        String email = jwtService.extractUsername(bearer.substring(7));
        return visitUseCase.obtainVisitorByResident(email);
    }

    @PutMapping(path = "/{documentResident}/visitor/{documentVisitor}")
    public void deleteVisitor(@PathVariable String documentResident, @PathVariable String documentVisitor) {
//
    }

    @PutMapping(path = "/qr/{qrId}")
    public void enableQR(@PathVariable Integer qrId) {
        //TODO: Enviar QR a telefono registrado sin guardarlo nuevamente el visitante
        //revisando con richard el envio
    }
    //TODO Enviar QR a telefono de wp que diga "presente este QR en porteria"
}
