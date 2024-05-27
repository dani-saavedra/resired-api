package com.resired.api.security.infraestructure.rest.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDTO(@JsonProperty("status_code") String statusCode,
                       @JsonProperty("status_message") String statusMessage) {

}
