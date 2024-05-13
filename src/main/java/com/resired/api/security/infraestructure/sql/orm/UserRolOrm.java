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
    @GeneratedValue
    private Integer id;

    @Column(name = "user_document")
    private String userId;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType rol;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "neighborhood_id")
    private NeighborhoodOrm neighborhood;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private HomeOrm home;

    @Column
    private LocalDateTime updateDate;

    public Rol converToEntity() {
        return new Rol(rol, neighborhood.getId(), neighborhood.getName(), home.getId(), home.getName());
    }
}
