package com.resired.api.admin.infraestructure.rest.controller;

import com.resired.api.admin.application.dto.NotificationResponseDto;
import com.resired.api.admin.application.usecase.AdminNotificationUseCase;
import com.resired.api.security.application.usecase.JwtService;
import com.resired.api.security.domain.entity.UserApp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
