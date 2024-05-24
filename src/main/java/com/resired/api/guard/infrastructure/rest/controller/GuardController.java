package com.resired.api.guard.infrastructure.rest.controller;

import com.resired.api.guard.application.usecase.AttendVisitUseCase;
import com.resired.api.guard.domain.entity.Visitor;
import com.resired.api.guard.infrastructure.rest.dto.InfoQrRequest;
import com.resired.api.resident.infraestructure.rest.dto.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/guard")
@AllArgsConstructor
public class GuardController {

    private final AttendVisitUseCase visitUseCase;

    @GetMapping("/info-qr")
    public Visitor obtainInfoQr(@RequestBody InfoQrRequest infoQrRequest) {
        return visitUseCase.validateInfoQR(infoQrRequest.qr());
    }

    @PostMapping("/visit")
    public ResponseData<String> registerVisit(@RequestBody InfoQrRequest infoQrRequest) {
        visitUseCase.registerVisit(infoQrRequest.qr());
        return new ResponseData<>("Registered visit successfully");
    }
}
