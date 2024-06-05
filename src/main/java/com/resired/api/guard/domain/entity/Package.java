package com.resired.api.guard.domain.entity;

import com.resired.api.resident.domain.enums.PackageStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Package {
    private Integer id;
    private final Integer receivedGuardId;
    private final Integer homeId;
    private final String receiver;
    private final String trackingNumber;
    private final String packageTransporter;
    private final String description;
    private PackageStatusEnum status;
    private final LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Integer deliveredGuardId;
    private String receiverLastFourDigits;

    public Package(Integer receivedGuardId, Integer homeId, String receiver,
                   String trackingNumber, String packageTransporter, String description) {
        this.receivedGuardId = receivedGuardId;
        this.homeId = homeId;
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.packageTransporter = packageTransporter;
        this.description = description;
        this.status = PackageStatusEnum.TO_COLLECT;
        this.createdDate = LocalDateTime.now();
    }


    public static Package createNewPackage(Integer receivedGuardId, Integer homeId, String receiver,
                                           String trackingNumber, String packageTransporter, String description) {
        return new Package(receivedGuardId, homeId, receiver, trackingNumber, packageTransporter, description);
    }

    public static Package fromExistingPackage(Integer id, Integer receivedGuardId, Integer homeId, String receiver,
                                              String trackingNumber, String packageTransporter, String description,
                                              PackageStatusEnum status, LocalDateTime createdDate,
                                              LocalDateTime updateDate, Integer deliveredGuardId,
                                              String receiverLastFourDigits) {
        return new Package(id, receivedGuardId, homeId, receiver, trackingNumber, packageTransporter,
            description, status, createdDate, updateDate, deliveredGuardId, receiverLastFourDigits);
    }

    public void deliverPackage(Integer deliveredGuardId, String receiverLastFourDigits) {
        this.status = PackageStatusEnum.DELIVERED;
        this.updateDate = LocalDateTime.now();
        this.deliveredGuardId = deliveredGuardId;
        this.receiverLastFourDigits = receiverLastFourDigits;
    }
}
