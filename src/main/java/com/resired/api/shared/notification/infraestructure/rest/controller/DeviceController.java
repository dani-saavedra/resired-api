package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.shared.notification.application.usecase.DeviceUseCase;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.PushNotification;
import com.resired.api.shared.notification.domain.port.PushNotificationPort;
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
    private final PushNotificationPort pushNotification;
    private final JwtService jwtService;

    @PostMapping
    @Operation(summary = "Save a new device for the user")
    public ResponseEntity<String> saveDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody Device request) {
        UserApp userApp = jwtService.extractUser(bearer);

        deviceUseCase.registerDevice(request, userApp.email(), userApp.userId());
        PushNotification notificationMessage = new PushNotification("Bienvenid@", "En ResiRed estamos para servirte");

        pushNotification.sendToDevice(notificationMessage, request);
        return ResponseEntity.ok("Device saved successfully");
    }

    @DeleteMapping
    @Operation(summary = "Delete a device of a user")
    public ResponseEntity<Void> deleteDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestParam("deviceId") String deviceId) {
        UserApp userApp = jwtService.extractUser(bearer);
        deviceUseCase.removeDevice(deviceId, userApp.email());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a device details")
    public ResponseEntity<Device> getDevice(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable String id) {
        UserApp userApp = jwtService.extractUser(bearer);
        Device device = deviceUseCase.getDevice(id, userApp.email());
        return ResponseEntity.ok(device);
    }

    @GetMapping
    @Operation(summary = "Get all devices for the user")
    public ResponseEntity<List<Device>> getDevices(
        @RequestHeader(value = "Authorization") String bearer
    ) {
        UserApp userApp = jwtService.extractUser(bearer);
        List<Device> devices = deviceUseCase.getDevicesByUser(userApp.email());

        return ResponseEntity.ok(devices);
    }

}
