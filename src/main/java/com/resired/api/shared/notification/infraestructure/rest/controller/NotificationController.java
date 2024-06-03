package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.shared.notification.application.dto.NewNotificationsResponse;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
import com.resired.api.shared.notification.domain.vo.NotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationUseCase notificationUseCase;
    private final JwtService jwtService;

    //TODO this controller is just for testing, it should be deleted for production
    @PostMapping("/home")
    public ResponseEntity<String> createNotificationForHome(
        @RequestBody NotificationHomeRequest request) {
        notificationUseCase.notifyHome(request);
        return ResponseEntity.ok("Sent");
    }

    //TODO this controller is just for testing, it should be deleted for production
    @PostMapping("/neighborhood")
    public ResponseEntity<String> createNotificationForNeighborhood(
        @RequestBody NotificationNeighborhoodRequest request) {
        notificationUseCase.notifyNeighborhood(request);
        return ResponseEntity.ok("Sent");
    }

    @GetMapping
    public ResponseEntity<List<NotificationMessage>> getAllNotificationsForUser(
        @RequestHeader(value = "Authorization") String bearer) {
        String email = jwtService.extractUsername(bearer.substring(7));

        return ResponseEntity.ok(
            notificationUseCase.listAllNotifications(email)
        );
    }

    @GetMapping("/new")
    public ResponseEntity<NewNotificationsResponse> checkThereIsNewNotifications(
        @RequestHeader(value = "Authorization") String bearer) {
        String email = jwtService.extractUsername(bearer.substring(7));

        return ResponseEntity.ok(
            notificationUseCase.thereAreNewNotifications(email)
        );
    }
}
