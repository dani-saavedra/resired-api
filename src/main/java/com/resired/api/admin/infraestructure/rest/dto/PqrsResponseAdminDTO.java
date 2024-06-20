package com.resired.api.admin.infraestructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PqrsResponseAdminDTO(@JsonProperty("ticket_number") String ticketNumber, String response) {
}
