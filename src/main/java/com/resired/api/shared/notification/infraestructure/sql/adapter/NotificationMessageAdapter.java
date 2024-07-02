package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import com.resired.api.shared.notification.domain.vo.NotificationForUser;
import com.resired.api.shared.notification.infraestructure.sql.jpa.NotificationJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.jpa.NotificationUserJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationOrm;
import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationUserOrm;
import com.resired.api.utils.FormatDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@AllArgsConstructor
public class NotificationMessageAdapter implements NotificationMessagePort {
    private final NotificationUserJpaRepository notificationUserRepository;
    private final UserJpaRepository userRepository;
    private final NotificationJpaRepository notificationRepository;

    @Override
    public List<NotificationForUser> getAllNotificationMessagesByEmail(String email) {
        Integer userId = userRepository.findByEmail(email).getId();
        return notificationUserRepository.findNotificationsByUserIdOrderByCreatedDateDesc(userId)
            .stream()
            .map((notificationOrm -> {
                NotificationMessage notificationMessage = new NotificationMessage(notificationOrm.getId(), notificationOrm.getTitle(),
                    notificationOrm.getMessage(), FormatDate.formatDate(notificationOrm.getCreatedDate()));
                return new NotificationForUser(notificationMessage,
                    true);
            }))
            .toList();
    }

    @Override
    public NotificationForUser getLastNotification(String email) {
        Integer userId = userRepository.findByEmail(email).getId();
        NotificationUserOrm notificationUserOrm = notificationUserRepository.findTopByUserIdOrderByCreatedDateDesc(userId);
        if (notificationUserOrm == null) return null;

        NotificationMessage notificationMessage = new NotificationMessage(
            notificationUserOrm.getNotification().getId(),
            notificationUserOrm.getNotification().getTitle(),
            notificationUserOrm.getNotification().getMessage(),
            FormatDate.formatDate(notificationUserOrm.getViewedAt()));
        return new NotificationForUser(notificationMessage,
            notificationUserOrm.getViewed());
    }

    @Transactional
    @Override
    public void markAllNotificationsAsRead(String email) {
        Integer userId = userRepository.findByEmail(email).getId();
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        notificationUserRepository.markAllNotificationsAsReadByUserId(userId, now);
    }

    @Override
    public void saveNotificationForHomeResidents(NotificationMessage notificationMessage, Integer homeId,
                                                 Integer neighborhoodId) {
        NotificationOrm notificationOrm = new NotificationOrm();
        notificationOrm.setTitle(notificationMessage.title());
        notificationOrm.setMessage(notificationMessage.message());
        notificationOrm.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        notificationOrm.setNeighborhoodId(neighborhoodId);
        notificationRepository.save(notificationOrm);

        List<UserOrm> residents = userRepository.findResidentsByHomeId(homeId);
        saveNotificationForResidents(notificationOrm, residents);
    }

    @Override
    public void saveNotificationForNeighborhoodResidents(NotificationMessage notificationMessage, Integer neighborhoodId) {
        NotificationOrm notificationOrm = new NotificationOrm();
        notificationOrm.setTitle(notificationMessage.title());
        notificationOrm.setMessage(notificationMessage.message());
        notificationOrm.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        notificationOrm.setNeighborhoodId(neighborhoodId);
        notificationRepository.save(notificationOrm);

        List<UserOrm> residents = userRepository.findResidentsByNeighborhoodId(neighborhoodId);

        saveNotificationForResidents(notificationOrm, residents);
    }

    @Override
    public List<NotificationMessage> getAllNotificationMessagesByNeighborhoodId(Integer neighborhoodId) {
        return notificationRepository.findAllByNeighborhoodId(neighborhoodId)
            .stream()
            .map(notificationOrm -> new NotificationMessage(notificationOrm.getId(),
                notificationOrm.getTitle(),
                notificationOrm.getMessage(),
                FormatDate.formatDate(notificationOrm.getCreatedDate()))).toList();
    }

    private void saveNotificationForResidents(NotificationOrm notificationOrm, List<UserOrm> residents) {
        residents.forEach(resident -> {
            NotificationUserOrm notificationUserOrm = new NotificationUserOrm();
            notificationUserOrm.setUserId(resident.getId());
            notificationUserOrm.setNotificationId(notificationOrm.getId());
            notificationUserOrm.setViewed(false);
            notificationUserOrm.setDeleted(false);
            notificationUserOrm.setViewedAt(null);
            notificationUserRepository.save(notificationUserOrm);
        });
    }
}
