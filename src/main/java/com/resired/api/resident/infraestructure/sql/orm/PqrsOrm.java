package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "pqrs")
@Data
@NoArgsConstructor
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

    @Column
    private String response;

    @ManyToOne
    @JoinColumn(name = "home")
    private HomeOrm home;

    public PqrsOrm(String title, String description, String ticketNumber, CategoryPQRS category, StatePQRS state,
                   Integer residentId, Integer neighborhoodId, Integer homeId) {
        this.title = title;
        this.description = description;
        this.ticketNumber = ticketNumber;
        this.category = category;
        this.state = state;
        UserOrm resident = new UserOrm();
        resident.setId(residentId);
        this.resident = resident;
        NeighborhoodOrm neighborhood = new NeighborhoodOrm();
        neighborhood.setId(neighborhoodId);
        this.neighborhood = neighborhood;
        this.creationDate = LocalDateTime.now(ZoneOffset.UTC);
        HomeOrm home = new HomeOrm();
        home.setId(homeId);
        this.home = home;
    }
}
