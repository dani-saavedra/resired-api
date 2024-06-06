package com.resired.api.admin.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static java.sql.Types.TINYINT;

@Entity
@Table(name = "notification_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCategoryOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;

    @Column
    private Integer neighborhoodId;

    @JdbcTypeCode(TINYINT)
    private boolean active;
}
