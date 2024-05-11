package com.resired.api.guard.infrastructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PACKAGE")
@Data
@AllArgsConstructor
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
    private String neighborhoodId;
}
