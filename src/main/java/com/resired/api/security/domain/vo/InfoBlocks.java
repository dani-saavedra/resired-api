package com.resired.api.security.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.admin.domain.vo.GroupingType;

import java.util.List;

public record InfoBlocks(@JsonProperty("grouping_type") GroupingType groupingType,
                         @JsonProperty("blocks") List<String> blocks) {
}
