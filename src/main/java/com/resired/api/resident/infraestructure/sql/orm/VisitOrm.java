package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "qr_id")
    private QrOrm qr;

    @Column
    private LocalDateTime checkIn;
}
