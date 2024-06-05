package com.resired.api.shared.notification.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationUserOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "notification_id")
    private Integer notificationId;

    @Column
    private Boolean viewed;

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    @Column
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id", insertable = false, updatable = false)
    private NotificationOrm notification;
}
