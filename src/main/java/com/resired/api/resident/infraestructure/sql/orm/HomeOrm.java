package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "HOME")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "home_type")
    private String homeType;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "neighborhood_id")
    private int neighborhoodId;

}
