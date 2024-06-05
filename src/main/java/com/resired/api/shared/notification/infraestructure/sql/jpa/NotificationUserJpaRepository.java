package com.resired.api.shared.notification.infraestructure.sql.jpa;

import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationOrm;
import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationUserOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationUserJpaRepository extends JpaRepository<NotificationUserOrm, Integer> {
    @Query("SELECT nu.notification FROM NotificationUserOrm nu WHERE nu.userId = :userId AND nu.deleted = true ORDER BY nu.notification.createdDate DESC")
    List<NotificationOrm> findNotificationsByUserIdOrderByCreatedDateDesc(Integer userId);

    @Query("SELECT nu FROM NotificationUserOrm nu WHERE nu.userId = :userId AND nu.deleted = false ORDER BY nu.notification.createdDate DESC")
    NotificationUserOrm findTopByUserIdOrderByCreatedDateDesc(Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationUserOrm nu SET nu.viewed = true, nu.viewedAt = :viewedAt WHERE nu.userId = :userId AND nu.viewed = false")
    void markAllNotificationsAsReadByUserId(Integer userId, LocalDateTime viewedAt);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationUserOrm nu SET nu.deleted = true WHERE nu.notificationId = :notificationId AND nu.userId = :userId")
    void softDeleteNotificationByNotificationIdAndUserId(Integer notificationId, Integer userId);
}
