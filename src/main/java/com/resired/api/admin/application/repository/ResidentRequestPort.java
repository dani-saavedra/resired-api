package com.resired.api.admin.application.repository;

import com.resired.api.admin.application.dto.ResidentRequestDto;

import java.util.List;

public interface ResidentRequestPort {

    void registerRequestResident(ResidentRequestDto residentRequestDto);

    List<ResidentRequestDto> obtainRequestResident(Integer neighborhood);

    boolean rejectRequestResident(Integer requestId, Integer neighborhood);
}
