package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "QR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_id")
    private Long homeId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column
    private boolean available;
}
