package com.resired.api.admin.domain.vo;

import lombok.Getter;

@Getter
public enum NeighborhoodCategory {
    EXTRA_SMALL(10), SMALL(40), MEDIUM(100),
    LARGE(300), EXTRA_LARGE(1000);
    private final Integer limitHomes;

    NeighborhoodCategory(Integer limitHomes) {
        this.limitHomes = limitHomes;
    }

    public boolean isInvalidQuantity(Integer homes) {
        return homes >= this.limitHomes;
    }
}
