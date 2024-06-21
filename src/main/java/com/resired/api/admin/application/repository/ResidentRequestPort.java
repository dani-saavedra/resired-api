package com.resired.api.admin.application.repository;

import com.resired.api.admin.application.dto.ResidentRequestDto;

public interface ResidentRequestPort {

    void registerRequestResident(ResidentRequestDto residentRequestDto);
}
