package com.resired.api.shared.notification.infraestructure.sql.jpa;

import com.resired.api.security.domain.enums.UserType;
import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceJpaRepository extends JpaRepository<DeviceOrm, String> {

    DeviceOrm findDeviceOrmByIdAndUserId(String deviceID, Integer userID);

    DeviceOrm findByUserId(Integer userId);

    @Query("SELECT d FROM DeviceOrm d " +
        "JOIN UserOrm u ON d.userId = u.id " +
        "JOIN UserRolOrm ur ON u.id = ur.user.id " +
        "WHERE ur.home.id = :homeId " +
        "AND ur.rol = :rol " +
        "AND ur.active = 1")
    List<DeviceOrm> findDevicesByHomeIdAndUserRole(@Param("homeId") Integer homeId, @Param("rol") UserType rol);

}
