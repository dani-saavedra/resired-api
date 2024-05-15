package com.resired.api.resident.domain.entity;

import com.resired.api.resident.domain.enums.PackageStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Package {

    private Long id;
    private String receiver;
    private String trackingNumber;
    private String packageTransporter;
    private String description;
    private PackageStatusEnum status;
    private LocalDateTime createdDate;
}
