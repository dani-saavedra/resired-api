package com.resired.api.shared.notification.domain.repository;

import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.shared.notification.domain.entity.Device;

import java.util.List;

public interface UserNotificationPort {

    List<Device> getAllDevicesByEmail(String email);

    Device getDeviceByIDAndEmail(String deviceID, String email);

    List<Integer> getNeighborhoodIdsForResidentByEmail(String email);

    List<BlockVo> getAllBlocksByUserEmail(String email);

    List<Device> getDevicesForHomeResident(Integer homeID);

    List<Device> getDevicesByUser(Integer userId);
}
