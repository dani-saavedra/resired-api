package com.resired.api.guard.domain.entity;

import com.resired.api.guard.domain.enums.PacketStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Packet {
    private String receiver;
    private String trackingNumber;
    private PacketStatus status;
    private String description;
    private String packageTransporter;
    private Long homeId;
}
