package com.resired.api.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BlockNeighborhood(@JsonProperty("grouping_type") GroupingType groupingType, List<BlockVo> blocks) {

}
