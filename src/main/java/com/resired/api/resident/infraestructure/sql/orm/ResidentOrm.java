package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table
public class ResidentOrm {

    @Id
    private UUID id;


    @Column
    private String firstName;
}
