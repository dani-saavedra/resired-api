package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NEIGHBORHOOD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodOrm {

    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String address;

}


