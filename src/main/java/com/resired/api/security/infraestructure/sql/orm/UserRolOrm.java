package com.resired.api.security.infraestructure.sql.orm;

import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.entity.Rol;
import com.resired.api.security.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserOrm user;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, columnDefinition = "VARCHAR(255)")
    private UserType rol;

    @Column
    private Integer active;

    public Boolean isActive() {
        return active == 1;
    }

    @ManyToOne
    @JoinColumn(name = "neighborhood_id")
    private NeighborhoodOrm neighborhood;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private HomeOrm home;

    @Column
    private LocalDateTime updateDate;

    @Column
    private LocalDateTime createdDate;

    public Rol converToEntity() {
        if (home != null) {
            return new Rol(rol, neighborhood.getId(), neighborhood.getName(), home.getId(), home.getName());
        } else {
            return new Rol(rol, neighborhood.getId(), neighborhood.getName());
        }
    }
}
