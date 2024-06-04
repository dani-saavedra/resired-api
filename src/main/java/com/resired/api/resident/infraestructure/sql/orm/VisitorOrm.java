package com.resired.api.resident.infraestructure.sql.orm;


import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;

import static java.sql.Types.TINYINT;

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

    @JdbcTypeCode(TINYINT)
    private boolean deleted;

    @JdbcTypeCode(TINYINT)
    private boolean favorite;

    public static VisitorOrm visitorFromResident(Integer userId, Integer homeId, String homeName, String name, String document,
                                                 String telephone, boolean isFavorite) {
        VisitorOrm result = getResult(userId, homeId, homeName, name, document, telephone);
        UserOrm authorizingUser = new UserOrm();
        authorizingUser.setId(userId);
        result.authorizingUser = authorizingUser;
        result.favorite = isFavorite;
        return result;
    }

    public static VisitorOrm visitorFromGuard(Integer userId, Integer homeId, String homeName, String name, String document, String telephone) {
        VisitorOrm result = getResult(userId, homeId, homeName, name, document, telephone);
        UserOrm authorizingUser = new UserOrm();
        authorizingUser.setId(userId);
        result.authorizingGuard = authorizingUser;
        return result;
    }

    private static VisitorOrm getResult(Integer userId, Integer homeId, String homeName, String name, String document, String telephone) {
        HomeOrm authorizingHome = new HomeOrm();
        authorizingHome.setId(homeId);
        authorizingHome.setName(homeName);

        VisitorOrm visitorOrm = new VisitorOrm();
        visitorOrm.name = name;
        visitorOrm.document = document;
        visitorOrm.authorizingHome = authorizingHome;
        visitorOrm.deleted = false;
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
