package com.resired.api.admin.application.usecase;

import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AdminHomesUseCase {

    private final HomePort homePort;


    public List<Home> getHomesByNeighborhood(Integer neighborhood) {
        return homePort.getHomesByNeighborhood(neighborhood);
    }

    public List<Home> getHomesByBlock(Integer block) {
        return homePort.getHomesByBlocks(block);
    }
}
