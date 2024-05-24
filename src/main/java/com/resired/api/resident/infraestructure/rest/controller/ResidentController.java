package com.resired.api.resident.infraestructure.rest.controller;

import com.resired.api.resident.application.dto.VisitorRequestDTO;
import com.resired.api.resident.application.usecase.VisitUseCase;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/resident/")
@AllArgsConstructor
public class ResidentController {

    private final VisitUseCase visitUseCase;

    @PostMapping(path = "/{documentResident}/visitor")
    public ResponseData<String> createVisitor(@PathVariable String documentResident, @RequestBody VisitorRequestDTO visitor) {
        String qr = visitUseCase.createVisitor(documentResident, visitor);
        return new ResponseData<>(qr);
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
