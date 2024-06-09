package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;
import com.resired.api.admin.infraestructure.sql.jpa.NeighborhoodAdmJpaRepository;
import com.resired.api.admin.infraestructure.sql.jpa.NotificationCategoryJpaRepository;
import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
@AllArgsConstructor
public class AdminNeighborhoodAdapterSQL implements NotificationCategoryPort, AdminNeighborhoodPort {

    private final NotificationCategoryJpaRepository notificationCategoryRepository;
    private final NeighborhoodAdmJpaRepository neighborhoodRepository;

    @Override
    public void createNewNotificationCategory(Integer neighborhoodId, String name, LevelNotificationEnum levelNotification) {
        NotificationCategoryOrm notificationCategoryOrm = new NotificationCategoryOrm();
        notificationCategoryOrm.setName(name);
        notificationCategoryOrm.setActive(true);
        notificationCategoryOrm.setNotificationCategory(levelNotification);
        notificationCategoryOrm.setNeighborhoodId(neighborhoodId);
        notificationCategoryRepository.save(notificationCategoryOrm);
    }

    public void deactivateNotificationCategory(Integer neighborhoodId, String name) {
        notificationCategoryRepository.deactivateNotificationCategory(neighborhoodId, name);
    }

    @Override
    public Integer createNeighborHood(String name, String city, String address, Integer stratum, String securityCompany) {
        NeighborhoodOrm entity = new NeighborhoodOrm(name, address, city, stratum,
            LocalDateTime.now(ZoneOffset.UTC), securityCompany);
        NeighborhoodOrm neighborhoodOrm = neighborhoodRepository.save(entity);
        return neighborhoodOrm.getId();
    }
}
