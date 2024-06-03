package com.resired.api.shared.notification.infraestructure.sql.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationOrm {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private LocalDateTime createdDate;
}
