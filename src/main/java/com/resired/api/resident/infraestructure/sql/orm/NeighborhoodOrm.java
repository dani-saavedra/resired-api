package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "NEIGHBORHOOD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodOrm {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

}


