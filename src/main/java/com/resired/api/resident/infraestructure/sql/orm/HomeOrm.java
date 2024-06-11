package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "HOME")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "block")
    @ManyToOne
    private BlockOrm block;

    @Column(name = "home_number")
    private String number;

    @Column(name = "type")
    private String homeType;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "owner_id")
    private Integer ownerId;

}
