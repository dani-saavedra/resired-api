package com.resired.api.admin.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateNeighborhood(String name, String city, String address,
                                 Integer stratum, @JsonProperty(value = "security_company") String securityCompany) {
}
