package com.resired.api.guard.domain.entity;

import com.resired.api.resident.domain.enums.PackageStatusEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Package {
    private final Integer guardId;
    private final Integer homeId;
    private final String receiver;
    private final String trackingNumber;
    private final String packageTransporter;
    private final String description;
    private final PackageStatusEnum status;
    private final LocalDateTime createdDate;

    private Package(Integer guardId, Integer homeId, String receiver,
                    String trackingNumber, String packageTransporter, String description,
                    PackageStatusEnum status, LocalDateTime createdDate) {
        this.guardId = guardId;
        this.homeId = homeId;
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.packageTransporter = packageTransporter;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
    }

    public static Package createNewPackage(Integer guardId, Integer homeId, String receiver,
                                           String trackingNumber, String packageTransporter, String description) {
        return new Package(guardId, homeId, receiver, trackingNumber,
            packageTransporter, description, PackageStatusEnum.TO_COLLECT, LocalDateTime.now());
    }

    public static Package fromExistingPackage(Integer guardId, Integer homeId, String receiver,
                                              String trackingNumber, String packageTransporter, String description,
                                              PackageStatusEnum status, LocalDateTime createdDate) {
        return new Package(guardId, homeId, receiver, trackingNumber,
            packageTransporter, description, status, createdDate);
    }
}
