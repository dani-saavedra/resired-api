package com.resired.api.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.DocumentTypeEnum;

public record CreateNeighborhoodVo(String name, String city, String address, Integer stratum,
                                   @JsonProperty("community_type") String communityType,
                                   NeighborhoodCategory category, AdminUser admin) {

    public record AdminUser(String email, @JsonProperty("document_type") DocumentTypeEnum documentType,
                            String document) {
    }
}
