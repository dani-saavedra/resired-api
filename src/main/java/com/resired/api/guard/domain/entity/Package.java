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

    public Package(Integer guardId, Integer homeId, String receiver, String trackingNumber, String packageTransporter, String description) {
        this.guardId = guardId;
        this.homeId = homeId;
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.packageTransporter = packageTransporter;
        this.description = description;
        this.status = PackageStatusEnum.TO_COLLECT;
        this.createdDate = LocalDateTime.now();
    }
}
