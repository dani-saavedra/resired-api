package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "pqrs")
@Data
public class PqrsOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String title;

    @Column(length = 5000)
    private String description;

    @Column
    private String ticketNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "VARCHAR(75)")
    private CategoryPQRS category;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "VARCHAR(75)")
    private StatePQRS state;

    @ManyToOne
    @JoinColumn(name = "resident", nullable = false)
    private UserOrm resident;

    @Column
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "neighborhood", nullable = false)
    private NeighborhoodOrm neighborhood;

    @Column
    private LocalDateTime responseDate;

    @ManyToOne
    @JoinColumn(name = "admin_responds")
    private UserOrm adminResponds;

}
