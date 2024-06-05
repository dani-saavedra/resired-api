package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.repository.UserNotificationPort;
import com.resired.api.shared.notification.infraestructure.sql.jpa.DeviceJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.jpa.NotificationUserJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserNotificationAdapter implements UserNotificationPort {
    private final DeviceJpaRepository deviceRepository;
    private final UserJpaRepository userRepository;
    private final NotificationUserJpaRepository notificationUserRepository;

    @Override
    public void addDevice(String email, Device device) {
        UserOrm user = userRepository.findByEmail(email);

        DeviceOrm deviceOrm = new DeviceOrm(device.getId(), user.getId(), device.getAllowNotifications());
        deviceRepository.save(deviceOrm);
    }

    @Override
    public List<Device> getAllDevicesByEmail(String email) {
        UserOrm user = userRepository.findByEmail(email);

        return user.getDevices().stream().map(this::convertToDevice).toList();
    }

    @Override
    public Device getDeviceByIDAndEmail(String deviceID, String email) {
        Integer userID = userRepository.findByEmail(email).getId();
        DeviceOrm device = deviceRepository.findDeviceOrmByIdAndUserId(deviceID, userID);

        if (device == null) return null;
        return convertToDevice(device);
    }

    @Override
    public List<Integer> getNeighborhoodIdsForResidentByEmail(String email) {
        return userRepository
            .findNeighborhoodsByUserEmailAndUserRole(email, UserType.RESIDENT)
            .stream().map(NeighborhoodOrm::getId)
            .toList();
    }

    @Override
    @Transactional
    public void deleteNotificationByID(String email, Integer notificationId) {
        Integer userId = userRepository.findByEmail(email).getId();
        notificationUserRepository.softDeleteNotificationByNotificationIdAndUserId(notificationId, userId);
    }

    private Device convertToDevice(DeviceOrm deviceOrm) {
        return new Device(deviceOrm.getId(), deviceOrm.getAllowNotifications());
    }
}
