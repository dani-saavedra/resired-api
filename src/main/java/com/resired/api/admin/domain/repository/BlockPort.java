package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.vo.GroupingType;

public interface BlockPort {

    void createBlock(Integer neighborhoodId, GroupingType type, String name, Integer homes);
}
