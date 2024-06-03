package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.shared.notification.application.usecase.DeviceUseCase;
import com.resired.api.shared.notification.domain.entity.Device;
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
    public ResponseEntity<String> saveDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody Device request) {
        UserApp userApp = jwtService.extractUser(bearer);

        deviceUseCase.registerDevice(request, userApp.email());
        return ResponseEntity.ok("Device saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        UserApp userApp = jwtService.extractUser(bearer);
        deviceUseCase.removeDevice(id, userApp.email());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        UserApp userApp = jwtService.extractUser(bearer);
        Device device = deviceUseCase.getDevice(id, userApp.email());
        return ResponseEntity.ok(device);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(
        @RequestHeader(value = "Authorization") String bearer
    ) {
        UserApp userApp = jwtService.extractUser(bearer);
        List<Device> devices = deviceUseCase.getDevicesByUser(userApp.email());

        return ResponseEntity.ok(devices);
    }

}
