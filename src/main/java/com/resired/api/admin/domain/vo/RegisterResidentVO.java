package com.resired.api.admin.domain.vo;

public record RegisterResidentVO(String documentId, String documentType, String firstName,
                                 String lastName, String email, Integer homeId, Integer neighborhoodId, String admin) {
}
