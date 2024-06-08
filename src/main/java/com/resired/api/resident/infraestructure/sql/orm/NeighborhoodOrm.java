package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NEIGHBORHOOD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private Integer socioeconomicLevel;

    @Column
    private LocalDateTime creationDate;

    public NeighborhoodOrm(String name, String address, String city, Integer socioeconomicLevel, LocalDateTime creationDate) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.socioeconomicLevel = socioeconomicLevel;
        this.creationDate = creationDate;
    }
}


