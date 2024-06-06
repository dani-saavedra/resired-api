package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.domain.repository.AdminNeighborhoodPort;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.admin.infraestructure.sql.jpa.NeighborhoodAdmJpaRepository;
import com.resired.api.admin.infraestructure.sql.jpa.NotificationCategoryJpaRepository;
import com.resired.api.admin.infraestructure.sql.orm.NotificationCategoryOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminNeighborhoodAdapterSQL implements NotificationCategoryPort, AdminNeighborhoodPort {

    private final NotificationCategoryJpaRepository notificationCategoryRepository;
    private final NeighborhoodAdmJpaRepository neighborhoodRepository;

    @Override
    public void createNewNotificationCategory(Integer neighborhoodId, String name) {
        NotificationCategoryOrm notificationCategoryOrm = new NotificationCategoryOrm();
        notificationCategoryOrm.setName(name);
        notificationCategoryOrm.setActive(true);
        notificationCategoryOrm.setNeighborhoodId(neighborhoodId);
        notificationCategoryRepository.save(notificationCategoryOrm);
    }

    public void deactivateNotificationCategory(Integer neighborhoodId, String name) {
        notificationCategoryRepository.deactivateNotificationCategory(neighborhoodId, name);
    }

    @Override
    public Integer createNeighborHood(String name, String city, String address) {
        NeighborhoodOrm neighborhoodOrm = neighborhoodRepository.save(new NeighborhoodOrm(name, address, city));
        return neighborhoodOrm.getId();
    }
}
