package com.resired.api.shared.notification.domain.repository;

import com.resired.api.admin.domain.vo.BlockVo;

import java.util.List;

public interface MassNotificationQueryPort {

    List<Integer> getNeighborhoodIdsForResidentByEmail(String email);

    List<BlockVo> getAllBlocksByUserEmail(String email);

}
