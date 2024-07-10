package com.resired.api.guard.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.PackageStatusEnum;
import com.resired.api.utils.FormatDate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class Package {

    @JsonProperty("package_id")
    private Integer id;
    @JsonProperty("received_guard_id")
    private Integer receivedGuardId;
    @JsonProperty("received_guard")
    private String receivedGuard;
    @JsonProperty("home_number")
    private String home;
    private Integer homeId;
    private String block;
    private String receiver;
    @JsonProperty("tracking_number")
    private String trackingNumber;
    @JsonProperty("package_transporter")
    private String packageTransporter;
    private String description;
    private PackageStatusEnum status;
    @JsonProperty("created_date")
    private String createdDate;
    @JsonProperty("deliver_date")
    private String updateDate;
    @JsonProperty("delivered_guard_id")
    private Integer deliveredGuardId;
    @JsonProperty("delivered_guard")
    private String deliveredGuard;
    private String receiverLastFourDigits;


    public static Package createNewPackage(Integer receivedGuardId, Integer homeId, String receiver,
                                           String trackingNumber, String packageTransporter, String description) {
        Package aPackage = new Package();
        aPackage.receivedGuardId = receivedGuardId;
        aPackage.homeId = homeId;
        aPackage.receiver = receiver;
        aPackage.trackingNumber = trackingNumber;
        aPackage.packageTransporter = packageTransporter;
        aPackage.description = description;
        aPackage.status = PackageStatusEnum.TO_COLLECT;
        return aPackage;
    }

    public static Package fromExistingPackage(Integer id, String receivedGuard, String home,
                                              String receiver, String trackingNumber, String packageTransporter,
                                              String description, PackageStatusEnum status, LocalDateTime createdDate,
                                              LocalDateTime updateDate, String deliveredGuard,
                                              String receiverLastFourDigits, String block) {
        Package aPackage = new Package();
        aPackage.receivedGuard = receivedGuard;
        aPackage.id = id;
        aPackage.home = home;
        aPackage.block = block;
        aPackage.receiver = receiver;
        aPackage.trackingNumber = trackingNumber;
        aPackage.packageTransporter = packageTransporter;
        aPackage.description = description;
        aPackage.status = status;
        aPackage.createdDate = FormatDate.formatDate(createdDate);
        aPackage.updateDate = FormatDate.formatDate(updateDate);
        aPackage.deliveredGuard = deliveredGuard;
        aPackage.receiverLastFourDigits = receiverLastFourDigits;
        return aPackage;
    }

    public void setDeliveredGuardId(Integer deliveredGuardId) {
        this.deliveredGuardId = deliveredGuardId;
    }
}
