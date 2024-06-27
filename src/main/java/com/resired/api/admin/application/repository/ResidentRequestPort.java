package com.resired.api.admin.application.repository;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.domain.vo.DecisionRequestResident;

import java.util.List;

public interface ResidentRequestPort {

    void registerRequestResident(ResidentRequestDto residentRequestDto);

    List<ResidentRequestDto> obtainRequestResident(Integer neighborhood, DecisionRequestResident decision);

    ResidentRequestDto obtainRequestResidentsById(Integer id);

    boolean rejectRequestResident(Integer requestId, Integer neighborhood);

    boolean acceptRequestResident(Integer id, Integer neighborhood);

}
