package com.resired.api.resident.infraestructure.sql.orm;


import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String document;

    //Autorizado siempre por casa, pero no siempre por residente
    //esto permite ver los visitantes registrados por residente de manera discriminada
    @ManyToOne
    @JoinColumn(name = "home_id")
    private HomeOrm authorizingHome;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private UserOrm authorizingUser;

    @ManyToOne
    @JoinColumn(name = "guard_id")
    private UserOrm authorizingGuard;

    @Column
    private LocalDateTime createdAt;
}
