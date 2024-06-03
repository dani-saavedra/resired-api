package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import com.resired.api.shared.notification.domain.vo.NotificationMessage;
import com.resired.api.shared.notification.infraestructure.sql.jpa.NotificationJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.jpa.NotificationUserJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationOrm;
import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationUserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class NotificationMessageAdapter implements NotificationMessagePort {
    private final NotificationUserJpaRepository notificationUserRepository;
    private final UserJpaRepository userRepository;
    private final NotificationJpaRepository notificationRepository;

    @Override
    public List<NotificationMessage> getAllNotificationMessagesByEmail(String email) {
        Integer userId = userRepository.findByEmail(email).getId();
        return notificationUserRepository.findNotificationsByUserIdOrderByCreatedDateDesc(userId)
            .stream()
            .map((notificationOrm -> new NotificationMessage(notificationOrm.getTitle(),
                notificationOrm.getMessage(), true)))
            .toList();
    }

    @Override
    public NotificationMessage getLastNotification(String email) {
        Integer userId = userRepository.findByEmail(email).getId();
        NotificationUserOrm notificationUserOrm = notificationUserRepository.findTopByUserIdOrderByCreatedDateDesc(userId);
        if (notificationUserOrm == null) return null;
        return new NotificationMessage(notificationUserOrm.getNotification().getTitle(),
            notificationUserOrm.getNotification().getMessage(),
            notificationUserOrm.getViewed());
    }

    @Transactional
    @Override
    public void markAllNotificationsAsRead(String email) {
        Integer userId = userRepository.findByEmail(email).getId();
        LocalDateTime now = LocalDateTime.now();
        notificationUserRepository.markAllNotificationsAsReadByUserId(userId, now);
    }

    @Override
    public void saveNotificationForHome(NotificationMessage notificationMessage, Integer homeId) {
        NotificationOrm notificationOrm = new NotificationOrm();
        notificationOrm.setTitle(notificationMessage.title());
        notificationOrm.setMessage(notificationMessage.message());
        notificationOrm.setCreatedDate(LocalDateTime.now());
        notificationRepository.save(notificationOrm);

        // TODO add notification to user in table notification_user
    }

    @Override
    public void saveNotificationForNeighborhood(NotificationMessage notificationMessage, Integer neighborhoodId) {
        // TODO finish implementation
    }
}
