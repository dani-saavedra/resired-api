package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.HomeDTO;
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


    public List<HomeDTO> getHomesByNeighborhood(Integer neighborhood) {
        return homePort.getHomesByNeighborhood(neighborhood)
            .stream()
            .map(home -> new HomeDTO(home.getId(), home.getName(), home.getBlock()))
            .toList();
    }

    public List<HomeDTO> getHomesByBlock(Integer block) {
        return homePort.getHomesByBlocks(block)
            .stream()
            .map(home -> new HomeDTO(home.getId(), home.getName(), home.getBlock()))
            .toList();
    }
}
