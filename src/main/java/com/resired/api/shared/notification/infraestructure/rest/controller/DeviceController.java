package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.shared.notification.application.usecase.DeviceUseCase;
import com.resired.api.shared.notification.domain.entity.Device;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/devices")
@AllArgsConstructor
public class DeviceController {
    private final DeviceUseCase deviceUseCase;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> saveDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody Device request) {
        String email = jwtService.extractUsername(bearer.substring(7));

        deviceUseCase.registerDevice(request, email);
        return ResponseEntity.ok("Device saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        String email = jwtService.extractUsername(bearer.substring(7));

        deviceUseCase.removeDevice(id, email);
        return ResponseEntity.noContent().build();
    }

}
