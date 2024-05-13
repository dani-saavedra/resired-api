package com.resired.api.guard.infrastructure.sql.orm;

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
    private String receiver;

    @Column
    private String trackingNumber;

    @Column
    private Long homeId;

    @Column
    private LocalDateTime receptionDate;

    @Column
    private String guardId;

    @Column
    private String status;

    @Column
    private LocalDateTime updateDate;

    @Column
    private String description;

    @Column
    private Long neighborhoodId;

    public PacketOrm(String receiver, String trackingNumber, Long homeId, LocalDateTime receptionDate, String guardId, String status, LocalDateTime updateDate, String description, Long neighborhoodId) {
        this.receiver = receiver;
        this.trackingNumber = trackingNumber;
        this.homeId = homeId;
        this.receptionDate = receptionDate;
        this.guardId = guardId;
        this.status = status;
        this.updateDate = updateDate;
        this.description = description;
        this.neighborhoodId = neighborhoodId;
    }
}
