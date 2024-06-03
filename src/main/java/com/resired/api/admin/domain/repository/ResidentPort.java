package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.vo.RegisterResidentVO;

public interface ResidentPort {

    void registerResident(RegisterResidentVO resident, String password, String registeredBy);

    void associateNewResidence(RegisterResidentVO resident, Integer userId);

    Integer getResidentIdByEmail(String email);
}
