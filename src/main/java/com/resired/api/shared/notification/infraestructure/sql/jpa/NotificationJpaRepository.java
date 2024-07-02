package com.resired.api.shared.notification.infraestructure.sql.jpa;

import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationOrm, Integer> {
    List<NotificationOrm> findAllByNeighborhoodId(Integer neighborhoodId);
}
