package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import com.resired.api.shared.notification.infraestructure.sql.jpa.NotificationJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NotificationMessageAdapter implements NotificationMessagePort {
    private final NotificationJpaRepository notificationRepository;

    @Override
    public List<NotificationMessage> getAllNotificationMessagesByNeighborhoodId(Integer neighborhoodId) {
        return notificationRepository.findAllByNeighborhoodId(neighborhoodId)
            .stream()
            .map(NotificationOrm::castToEntity).toList();
    }
}


