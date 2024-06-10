package com.resired.api.admin.domain.vo;

public record NeighConfig(Integer id, ResidenceType residenceType,
                          GroupingType groupingType, String preferredName, NeighborhoodCategory category,
                          Integer towers, Integer homes) {
}
