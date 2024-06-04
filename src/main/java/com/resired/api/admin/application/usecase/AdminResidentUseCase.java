package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.repository.AdminResidentPort;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.exception.InvalidHomeException;
import com.resired.api.resident.domain.repository.HomePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class AdminResidentUseCase {

    private final AdminResidentPort adminResidentPort;
    private final HomePort homePort;

    public void removeResidentsByHome(Integer neighborhoodId, Integer idHome) {
        Home home = homePort.getHomeById(idHome);
        if(home == null || !Objects.equals(home.getNeighborhoodId(), neighborhoodId)){
            throw new InvalidHomeException(idHome);
        }
        adminResidentPort.removeUserByHome(idHome);
    }
}
