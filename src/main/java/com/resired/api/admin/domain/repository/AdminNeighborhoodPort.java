package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.vo.CreateNeighborhoodVo;
import com.resired.api.admin.domain.vo.NeighConfig;

public interface AdminNeighborhoodPort {

    Integer createNeighborHood(CreateNeighborhoodVo createNeighborhoodVo);

    void configNeighborhood(NeighConfig neighConfig);
}
