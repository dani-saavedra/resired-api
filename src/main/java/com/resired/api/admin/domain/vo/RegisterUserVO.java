package com.resired.api.admin.domain.vo;

import com.resired.api.resident.domain.enums.DocumentTypeEnum;
import com.resired.api.security.domain.enums.UserType;

public record RegisterUserVO(String documentId, DocumentTypeEnum documentType, String firstName,
                             String lastName, String email, Integer neighborhoodId, Integer homeId, UserType userType) {
}
