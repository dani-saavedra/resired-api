package com.resired.api.shared.notification.infraestructure.sql.orm;

import com.resired.api.admin.domain.vo.LevelNotificationEnum;
import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.utils.FormatDate;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", columnDefinition = "VARCHAR(20)")
    private LevelNotificationEnum level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", updatable = false)
    private NotificationCategoryOrm categoryOrm;

    public NotificationMessage castToEntity() {
        return new NotificationMessage(
            getId(),
            getTitle(),
            getMessage(),
            FormatDate.formatDate(getCreatedDate()),
            getCategoryOrm().castToEntity(),
            getLevel()
        );
    }
}
