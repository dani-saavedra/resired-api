package com.resired.api.security.infraestructure.sql.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_APP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrm {

    @Id
    private String documentId;

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
