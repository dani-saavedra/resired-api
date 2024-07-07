package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.vo.BlockNeighborhood;
import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.admin.domain.vo.GroupingType;

import java.util.List;

public interface BlockPort {

    void createBlock(Integer neighborhoodId, GroupingType type, String name, List<String> homes);

    BlockNeighborhood getBlocksByNeighborhoodId(Integer neighborhoodId);

    BlockVo getBlockByIdAndNeighbor(Integer blockId, Integer neighborhoodId);
}
