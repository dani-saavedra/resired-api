package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
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
    private final NotificationUseCase notificationUseCase;

    //TODO this controller is just for testing, it should be deleted for production
    @PostMapping("/home")
    public ResponseEntity<String> createNotificationForHome
    (@RequestBody NotificationHomeRequest request) {
        notificationUseCase.notifyHome(request);
        return ResponseEntity.ok("Sent");
    }

    //TODO this controller is just for testing, it should be deleted for production
    @PostMapping("/neighborhood")
    public ResponseEntity<String> createNotificationForNeighborhood
    (@RequestBody NotificationNeighborhoodRequest request) {
        notificationUseCase.notifyNeighborhood(request);
        return ResponseEntity.ok("Sent");
    }
}
