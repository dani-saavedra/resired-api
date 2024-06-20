package com.resired.api.admin.application.dto;

public record ResponsePQRS(String ticketNumber, String response, Integer userResponse,
                           Integer neighborhood) {
}
