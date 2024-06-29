package com.resired.api.security.infraestructure.sql.orm;

import com.resired.api.security.domain.entity.User;
import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String documentId;

    @Column
    private String documentType;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRolOrm> userRols;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<DeviceOrm> devices;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private Integer active;

    @Column
    private String registeredBy;

    public Boolean isActive() {
        return active == 1;
    }

    public User toEntity() {
        return new User(
            this.id,
            this.documentId,
            this.firstName,
            this.email,
            this.lastName,
            this.isActive(),
            this.userRols.stream()
                .filter(UserRolOrm::isActive)
                .map(UserRolOrm::converToEntity)
                .toList()
        );
    }

    @Override
    public String toString() {
        return "UserOrm{" +
            "documentId='" + documentId + '\'' +
            ", documentType='" + documentType + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

    public UserOrm(Integer id) {
        this.id = id;
    }

    public String fullName(){
        return firstName + " " + lastName;
    }
}
