package com.resired.api.shared.notification.infraestructure.rest.controller;

import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.shared.notification.application.dto.NewNotificationsResponse;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
import com.resired.api.shared.notification.domain.vo.NotificationMessage;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get all notifications for a user (not deleted notifications),"
        + "this also change the state of all these notifications to viewed")
    public ResponseEntity<List<NotificationMessage>> getAllNotificationsForUser(
        @RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);

        return ResponseEntity.ok(
            notificationUseCase.listAllNotifications(userApp.email())
        );
    }

    @GetMapping("/new")
    @Operation(summary = "Check if there are new notifications available (notifications not viewed)")
    public ResponseEntity<NewNotificationsResponse> checkThereIsNewNotifications(
        @RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);

        return ResponseEntity.ok(
            notificationUseCase.thereAreNewNotifications(userApp.email())
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Makes a soft delete over a given notification for the user authenticated")
    public ResponseEntity<String> removeNotificationForUser(
        @RequestHeader(value = "Authorization") String bearer,
        @PathVariable Integer id) {
        UserApp userApp = jwtService.extractUser(bearer);
        notificationUseCase.deleteNotificationForUser(id, userApp.email());
        return ResponseEntity.ok("Notification removed successfully");
    }
}
