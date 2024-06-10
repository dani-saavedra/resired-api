package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;
import com.resired.api.admin.domain.vo.NeighConfig;
import com.resired.api.admin.infraestructure.sql.jpa.NeighborhoodAdmJpaRepository;
import com.resired.api.admin.infraestructure.sql.jpa.NotificationCategoryJpaRepository;
import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Override
    public void configNeighborhood(NeighConfig neighConfig) {
        NeighborhoodOrm neighborhoodOrm = new NeighborhoodOrm();
        neighborhoodOrm.setId(neighConfig.id());
        neighborhoodOrm.setCategory(neighborhoodOrm.getCategory());
        neighborhoodOrm.setUpdateDate(LocalDate.now(ZoneOffset.UTC));
        neighborhoodOrm.setHomes(neighConfig.homes());
        neighborhoodOrm.setTowers(neighConfig.towers());
        neighborhoodRepository.save(neighborhoodOrm);
    }

    public void deactivateNotificationCategory(Integer neighborhoodId, String name) {
        notificationCategoryRepository.deactivateNotificationCategory(neighborhoodId, name);
    }

    @Override
    public Integer createNeighborHood(CreateNeighborhoodVo neighbor) {
        NeighborhoodOrm entity = new NeighborhoodOrm(neighbor.name(), neighbor.address(), neighbor.city(), neighbor.stratum(), neighbor
            .communityType(), neighbor.category());
        NeighborhoodOrm neighborhoodOrm = neighborhoodRepository.save(entity);
        return neighborhoodOrm.getId();
    }
}
