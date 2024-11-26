package com.resired.api.shared.notification.infraestructure.sql.adapter;

import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.shared.notification.domain.repository.MassNotificationQueryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MassNotificationQueryAdapter implements MassNotificationQueryPort {

    private final UserJpaRepository userRepository;

    @Override
    public List<Integer> getNeighborhoodIdsForResidentByEmail(String email) {
        return userRepository
            .findNeighborhoodsByUserEmailAndUserRole(email, UserType.RESIDENT)
            .stream()
            .map(NeighborhoodOrm::getId)
            .toList();
    }

    @Override
    public List<BlockVo> getAllBlocksByUserEmail(String email) {
        return userRepository.findBlockOrmsByResidentEmail(email)
            .stream()
            .map(blockOrm -> new BlockVo(blockOrm.getId(),
                blockOrm.getName()))
            .toList();
    }
}
