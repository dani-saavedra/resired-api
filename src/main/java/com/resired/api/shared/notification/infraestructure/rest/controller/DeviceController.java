package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.shared.notification.application.usecase.DeviceUseCase;
import com.resired.api.shared.notification.domain.entity.Device;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

        URI uriResource = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(request.getId()).toUri();

        return ResponseEntity.created(uriResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        String email = jwtService.extractUsername(bearer.substring(7));

        deviceUseCase.removeDevice(id, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        String email = jwtService.extractUsername(bearer.substring(7));
        Device device = deviceUseCase.getDevice(id, email);
        return ResponseEntity.ok(device);
    }

}
