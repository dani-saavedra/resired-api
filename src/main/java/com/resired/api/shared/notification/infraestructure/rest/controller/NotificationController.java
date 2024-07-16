package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/notifications")
@AllArgsConstructor
public class NotificationController {
    private final PushAppUseCase pushAppUseCase;
    private final JwtService jwtService;

    @Deprecated
    @PostMapping("/home")
    public ResponseEntity<String> createNotificationForHome(
        @RequestBody NotificationHomeRequest request) {
        pushAppUseCase.notifyHome(request);
        return ResponseEntity.ok("Sent");
    }

    @Deprecated
    @PostMapping("/neighborhood")
    public ResponseEntity<String> createNotificationForNeighborhood(
        @RequestBody NotificationNeighborhoodRequest request) {
        pushAppUseCase.notifyNeighborhood(request);
        return ResponseEntity.ok("Sent");
    }
}
