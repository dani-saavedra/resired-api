package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.shared.notification.application.usecase.DeviceUseCase;
import com.resired.api.shared.notification.domain.entity.Device;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(
        @RequestHeader(value = "Authorization") String bearer
    ) {
        String email = jwtService.extractUsername(bearer.substring(7));
        List<Device> devices = deviceUseCase.getDevicesByUser(email);

        return ResponseEntity.ok(devices);
    }

}
