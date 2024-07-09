package com.resired.api.shared.notification.domain.repository;

import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.shared.notification.domain.entity.Device;

import java.util.List;

public interface UserNotificationPort {
    void addDevice(String email, Device device);

    List<Device> getAllDevicesByEmail(String email);

    Device getDeviceByIDAndEmail(String deviceID, String email);

    List<Integer> getNeighborhoodIdsForResidentByEmail(String email);

    List<BlockVo> getAllBlocksByUserId(Integer id);

}
