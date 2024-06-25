package com.resired.api.admin.application.usecase;

import com.resired.api.admin.domain.repository.BlockPort;
import com.resired.api.admin.domain.vo.BlockVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminBlocksUseCase {
    private final BlockPort blockRepository;

    public List<BlockVo> getAllBlocksOfNeighborhood(Integer neighborhoodId) {
        return blockRepository.getAllBlocksByNeighborhoodId(neighborhoodId);
    }
}
