package com.resired.api.guard.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InfoQrRequest(String qr, String document, @JsonProperty("car_plate_id") String carPlateId) {

}
