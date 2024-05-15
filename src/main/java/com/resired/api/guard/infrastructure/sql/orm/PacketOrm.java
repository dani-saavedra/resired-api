package com.resired.api.guard.infrastructure.sql.orm;

import com.resired.api.guard.domain.enums.PacketStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "package")
@Data
@NoArgsConstructor
public class PacketOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String guardId;

    @Column
    private Long homeId;

    @Column
    private String receiver;

    @Column
    private String trackingNumber;

    @Column
    private String packageTransporter;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private PacketStatus status;

    @Column(name = "received_date")
    private LocalDateTime receptionDate;

    @Column
    private LocalDateTime updateDate;

    public PacketOrm(String guardId, Long homeId, String receiver, String trackingNumber, String packageTransporter, String description, PacketStatus status, LocalDateTime receptionDate, LocalDateTime updateDate) {
        this.guardId = guardId;
        this.homeId = homeId;
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.packageTransporter = packageTransporter;
        this.description = description;
        this.status = status;
        this.receptionDate = receptionDate;
        this.updateDate = updateDate;
    }
}
