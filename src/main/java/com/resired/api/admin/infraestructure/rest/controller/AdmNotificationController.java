package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.*;
import com.resired.api.shared.notification.application.usecase.NotificationUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.shared.notification.application.usecase.NotificationManagementUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmNotificationController {
    private final NotificationUseCase notificationUseCase;
    private final NotificationManagementUseCase notificationManagement;
    private final JwtService jwtService;

    @GetMapping(path = "/notifications")
    @Operation(summary = "Obtain all notifications sent from Neighborhood")
    public List<NotificationResponseDto> getNotificationsByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return notificationManagement.getAllNotificationsByNeighborhoodId(userApp.neighborhoodId());
    }

    @PostMapping("/notifications/neighborhood")
    @Operation(summary = "Create a new notification and send to all the neighborhood" +
        " (this operation also saves in the database")
    public ResponseEntity<String> createNotificationForNeighborhood(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody CreateNotificationDto request) {
        UserApp userApp = jwtService.extractUser(bearer);
        notificationUseCase.sendNotification(request, userApp.neighborhoodId());
        return ResponseEntity.ok("Sent");
    }

    @GetMapping(path = "/notifications/categories")
    @Operation(summary = "Obtain all notification categories from Neighborhood")
    public List<NotificationCategoryDto> getCategories(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return notificationManagement.getAllNotificationCategories(userApp.neighborhoodId());
    }

    @PostMapping("/notifications/categories")
    @Operation(summary = "Create a new notification category")
    public ResponseEntity<String> createNotificationCategoryForNeighborhood(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody NotificationCategoryRequestDto request) {
        UserApp userApp = jwtService.extractUser(bearer);
        notificationManagement.createNotificationCategory(request, userApp.neighborhoodId());
        return ResponseEntity.ok("Created");
    }

    @PostMapping("/notifications/blocks")
    @Operation(summary = "Create a new notification and send to all the list of blocks" +
        " given (this operation also saves in the database")
    public ResponseEntity<String> createNotificationForBlocks(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody CreateNotificationForBlocksDto request) {
        notificationUseCase.sendNotification(request);
        return ResponseEntity.ok("Sent");
    }

    @PostMapping("/notifications/homes")
    @Operation(summary = "Create a new notification and send to all the list of homes" +
        " given (this operation also saves in the database")
    public ResponseEntity<String> createNotificationForHomes(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody CreateNotificationForHomesDto request) {
        notificationUseCase.sendNotification(request);
        return ResponseEntity.ok("Sent");
    }
}
