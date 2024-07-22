package com.resired.api.guard.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Visit {
    Integer id;
    String visitorName;
    String visitorDocument;
    String destinationHome;
    LocalDateTime checkIn;
    String authorizingGuardName;
    String vehiclePlateId;
    LocalDateTime checkoutDate;
}
