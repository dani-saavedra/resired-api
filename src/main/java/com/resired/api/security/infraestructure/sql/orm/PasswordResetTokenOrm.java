package com.resired.api.security.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;

import static java.sql.Types.TINYINT;

@Entity
@Table(name = "pass_reset_token")
@Data
@NoArgsConstructor
public class PasswordResetTokenOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String token;
    @Column
    private Integer userId;
    @Column
    private LocalDateTime expiryDate;
    @JdbcTypeCode(TINYINT)
    private boolean invalid;

    public PasswordResetTokenOrm(Integer userId, String token, LocalDateTime expiryDate) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }
}
