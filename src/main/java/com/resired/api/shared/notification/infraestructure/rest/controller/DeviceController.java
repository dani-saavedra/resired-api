package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.shared.notification.application.usecase.DeviceUseCase;
import com.resired.api.shared.notification.domain.entity.Device;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/devices")
@AllArgsConstructor
public class DeviceController {
    private final DeviceUseCase deviceUseCase;
    private final JwtService jwtService;

    @PostMapping
    @Operation(summary = "Save a new device for the user")
    public ResponseEntity<String> saveDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody Device request) {
        String email = jwtService.extractUsername(bearer.substring(7));

        deviceUseCase.registerDevice(request, email);
        return ResponseEntity.ok("Device saved successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a device of a user")
    public ResponseEntity<Void> deleteDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        String email = jwtService.extractUsername(bearer.substring(7));

        deviceUseCase.removeDevice(id, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a device details")
    public ResponseEntity<Device> getDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        String email = jwtService.extractUsername(bearer.substring(7));
        Device device = deviceUseCase.getDevice(id, email);
        return ResponseEntity.ok(device);
    }

    @GetMapping
    @Operation(summary = "Get all devices for the user")
    public ResponseEntity<List<Device>> getDevices(
        @RequestHeader(value = "Authorization") String bearer) {
        String email = jwtService.extractUsername(bearer.substring(7));
        List<Device> devices = deviceUseCase.getDevicesByUser(email);

        return ResponseEntity.ok(devices);
    }

}
