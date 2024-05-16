package com.resired.api.security.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_APP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrm {

    @Id
    @Column(name = "document_id")
    private String userId;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<UserRolOrm> userRols;

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
    private boolean active;
}
