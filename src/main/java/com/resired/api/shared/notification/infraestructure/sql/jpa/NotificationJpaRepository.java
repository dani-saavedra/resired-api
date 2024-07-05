package com.resired.api.shared.notification.infraestructure.sql.jpa;

import com.resired.api.shared.notification.infraestructure.sql.orm.NotificationOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationOrm, Integer> {

    @Query("SELECT no FROM NotificationOrm no WHERE no.categoryOrm.neighborhoodId = :neighborhoodId")
    List<NotificationOrm> findAllByNeighborhoodId(Integer neighborhoodId);
}
