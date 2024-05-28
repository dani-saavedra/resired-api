package com.resired.api.shared.notification.infraestructure.sql.jpa;

import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DeviceJpaRepository extends JpaRepository<DeviceOrm, String> {
    void deleteDeviceOrmById(String id);

    @Modifying
    @Query("update DeviceOrm d set d.allowNotifications = ?2 where d.id = ?1")
    void changeNotificationPermission(String id, Boolean status);

}
