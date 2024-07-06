package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.CreateNotificationDto;
import com.resired.api.admin.application.dto.NotificationCategoryResponseDto;
import com.resired.api.admin.application.dto.NotificationResponseDto;
import com.resired.api.admin.application.usecase.AdminNotificationUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
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
    private final AdminNotificationUseCase adminNotificationUseCase;
    private final JwtService jwtService;

    @GetMapping(path = "/notifications")
    @Operation(summary = "Obtain all notifications sent from Neighborhood")
    public List<NotificationResponseDto> getNotificationsByNeighborhood(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminNotificationUseCase.getAllNotificationsByNeighborhoodId(userApp.neighborhoodId());
    }

    @PostMapping("/notifications/neighborhood")
    @Operation(summary = "Create a new notification and send to all the neighborhood" +
        " (this operation also saves in the database")
    public ResponseEntity<String> createNotificationForNeighborhood(
        @RequestHeader(value = "Authorization") String bearer,
        @RequestBody CreateNotificationDto request) {
        UserApp userApp = jwtService.extractUser(bearer);
        adminNotificationUseCase.sendNotificationToNeighborhood(request, userApp.neighborhoodId());
        return ResponseEntity.ok("Sent");
    }

    @GetMapping(path = "/notifications/categories")
    @Operation(summary = "Obtain all notification categories from Neighborhood")
    public List<NotificationCategoryResponseDto> getCategories(@RequestHeader(value = "Authorization") String bearer) {
        UserApp userApp = jwtService.extractUser(bearer);
        return adminNotificationUseCase.getAllNotificationCategories(userApp.neighborhoodId());
    }
}
