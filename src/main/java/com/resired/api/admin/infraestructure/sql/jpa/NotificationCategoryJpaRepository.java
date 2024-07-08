package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationCategoryJpaRepository extends JpaRepository<NotificationCategoryOrm, Integer> {

    @Modifying
    @Query("update NotificationCategoryOrm nc set nc.active = false where nc.neighborhoodId =:neighborhoodId and nc.name =:name ")
    void deactivateNotificationCategory(Integer neighborhoodId, String name);

    List<NotificationCategoryOrm> findAllByNeighborhoodId(Integer id);
}
