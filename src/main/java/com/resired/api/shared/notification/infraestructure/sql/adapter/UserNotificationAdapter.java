package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.repository.UserNotificationPort;
import com.resired.api.shared.notification.infraestructure.sql.jpa.DeviceJpaRepository;
import com.resired.api.shared.notification.infraestructure.sql.orm.DeviceOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserNotificationAdapter implements UserNotificationPort {
    private final DeviceJpaRepository deviceRepository;
    private final UserJpaRepository userRepository;


    @Override
    public List<Device> getAllDevicesByEmail(String email) {
        UserOrm user = userRepository.findByEmail(email);

        return user.getDevices()
            .stream()
            .map(this::convertToDevice)
            .toList();
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
            .stream()
            .map(NeighborhoodOrm::getId)
            .toList();
    }

    @Override
    public List<BlockVo> getAllBlocksByUserId(Integer id) {
        return userRepository.findBlockOrmsByResidentId(id)
            .stream()
            .map(blockOrm -> new BlockVo(blockOrm.getId(),
                blockOrm.getName()))
            .toList();
    }

    @Override
    public List<Device> getDevicesForHomeResident(Integer homeID) {
        return deviceRepository.findDevicesByHomeIdAndUserRole(homeID, UserType.RESIDENT)
            .stream()
            .map(deviceOrm -> new Device(deviceOrm.getId()))
            .toList();
    }

    @Override
    public List<Device> getDevicesByUser(Integer userId) {
        return deviceRepository.findByUserId(userId)
            .stream()
            .map(orm -> new Device(orm.getId()))
            .toList();
    }

    private Device convertToDevice(DeviceOrm deviceOrm) {
        return new Device(deviceOrm.getId());
    }
}
