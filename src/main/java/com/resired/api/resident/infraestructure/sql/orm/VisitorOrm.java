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
@NoArgsConstructor
public class VisitorOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String document;

    @Column
    private String telephone;

    //Autorizado siempre por casa, pero no siempre por residente
    //esto permite ver los visitantes registrados por residente de manera discriminada
    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column
    private Integer deleted;

    public Boolean isDelete() {
        return deleted == 1;
    }

    public static VisitorOrm visitorFromResident(Integer userId, Integer homeId, String homeName, String name, String document, String telephone) {
        HomeOrm authorizingHome = new HomeOrm();
        authorizingHome.setId(homeId);
        authorizingHome.setName(homeName);
        UserOrm authorizingUser = new UserOrm();
        authorizingUser.setId(userId);
        VisitorOrm visitorOrm = new VisitorOrm();
        visitorOrm.name = name;
        visitorOrm.document = document;
        visitorOrm.authorizingHome = authorizingHome;
        visitorOrm.authorizingUser = authorizingUser;
        visitorOrm.deleted = 0;
        visitorOrm.createdAt = LocalDateTime.now();
        visitorOrm.telephone = telephone;
        return visitorOrm;
    }

    @Override
    public String toString() {
        return "{" +
            "name:'" + name + '\'' +
            ", document:'" + document + '\'' +
            ", authorizingHome:" + authorizingHome.getName() +
            ", createdAt:" + createdAt +
            '}';
    }
}
