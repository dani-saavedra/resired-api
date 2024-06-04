package com.resired.api.guard.domain.entity;

import com.resired.api.resident.domain.enums.PackageStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Package {
    private final Integer id;
    private final Integer guardId;
    private final Integer homeId;
    private final String receiver;
    private final String trackingNumber;
    private final String packageTransporter;
    private final String description;
    private final PackageStatusEnum status;
    private final LocalDateTime createdDate;

    public static Package createNewPackage(Integer guardId, Integer homeId, String receiver,
                                           String trackingNumber, String packageTransporter, String description) {
        return new Package(0, guardId, homeId, receiver, trackingNumber,
            packageTransporter, description, PackageStatusEnum.TO_COLLECT, LocalDateTime.now());
    }

    public static Package fromExistingPackage(Integer id, Integer guardId, Integer homeId, String receiver,
                                              String trackingNumber, String packageTransporter, String description,
                                              PackageStatusEnum status, LocalDateTime createdDate) {
        return new Package(id, guardId, homeId, receiver, trackingNumber,
            packageTransporter, description, status, createdDate);
    }
}
