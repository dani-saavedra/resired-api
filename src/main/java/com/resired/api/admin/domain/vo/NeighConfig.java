package com.resired.api.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NeighConfig(@JsonProperty(required = true) Integer id,
                          @JsonProperty(value = "residence_type", required = true) ResidenceType residenceType,
                          @JsonProperty(value = "grouping_type", required = true) GroupingType groupingType,
                          @JsonProperty(value = "preferred_name", required = true) String preferredName,
                          @JsonProperty(value = "grouping_homes", required = true) List<GroupingHomes> groupingHomes,
                          @JsonProperty(value = "security_company") String securityCompany) {
    public record GroupingHomes(String tower, Integer homes) {

    }
}
