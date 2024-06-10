package com.resired.api.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NeighConfig(@JsonProperty(required = true) Integer id,
                          @JsonProperty(value = "residence_type", required = true) ResidenceType residenceType,
                          @JsonProperty(value = "grouping_type", required = true) GroupingType groupingType,
                          @JsonProperty(value = "preferred_name", required = true) String preferredName,
                          @JsonProperty(required = true) Integer towers,
                          @JsonProperty(required = true) Integer homes) {
}
