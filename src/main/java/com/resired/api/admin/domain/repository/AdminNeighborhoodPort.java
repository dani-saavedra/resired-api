package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.entity.Neighborhood;
import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.NeighConfig;

public interface AdminNeighborhoodPort {

    Integer createNeighborHood(CreateNeighborhoodVo createNeighborhoodVo);

    Neighborhood findNeighborhoodById(Integer id);

    void configNeighborhood(NeighConfig neighConfig, int towers, int homes);
}
