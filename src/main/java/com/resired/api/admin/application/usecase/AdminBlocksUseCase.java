package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.vo.BlockNeighborhood;
import com.resired.api.admin.domain.repository.BlockPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminBlocksUseCase {
    private final BlockPort blockRepository;

    public BlockNeighborhood getAllBlocksOfNeighborhood(Integer neighborhoodId) {
        return blockRepository.getBlocksByNeighborhoodId(neighborhoodId);
    }
}
