package com.resired.api.shared.notification.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceOrm {

    @Id
    @Column(name = "id")
    private String id;

    @JoinColumn(name = "user_id", nullable = false)
    private Integer userId;
}
