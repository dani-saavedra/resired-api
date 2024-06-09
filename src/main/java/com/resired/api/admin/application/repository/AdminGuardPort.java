package com.resired.api.admin.application.repository;

import com.resired.api.admin.application.dto.GuardDto;

import java.util.List;

public interface AdminGuardPort {
    List<GuardDto> getAllGuards(Integer neighborhoodId, int active);
}
