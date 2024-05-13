package com.resired.api.guard.domain.entity;

import com.resired.api.guard.domain.enums.PacketStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Packet {
    @Setter
    private String receiver;
    @Setter
    private String trackingNumber;
    @Setter
    private PacketStatus status;
    @Setter
    private String description;

    public Packet(String receiver, String trackingNumber, PacketStatus status, String description) {
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.description = description;
    }
}
