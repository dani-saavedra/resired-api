package com.resired.api.guard.domain.entity;

import com.resired.api.guard.domain.entity.enums.PacketStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Packet {
    private Long id;
    @Setter
    private String receiver;
    @Setter
    private String trackingNumber;
    @Setter
    private PacketStatus status;
    @Setter
    private LocalDateTime collectedDate;
    @Setter
    private String description;

    public Packet(Long id, String receiver, String trackingNumber, PacketStatus status, LocalDateTime collectedDate, String description) {
        this.id = id;
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.collectedDate = collectedDate;
        this.description = description;
    }
}
