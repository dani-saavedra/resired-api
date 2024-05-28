package com.resired.api.security.infraestructure.sql.orm;

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

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<UserRolOrm> userRols;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
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

    public Boolean isActive() {
        return active == 1;
    }
}
