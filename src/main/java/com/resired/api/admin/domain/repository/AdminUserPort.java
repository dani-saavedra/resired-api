package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.entity.Resident;
import com.resired.api.admin.domain.vo.RegisterUserVO;

import java.util.List;

public interface AdminUserPort {

    void registerUserToNeighborhood(RegisterUserVO resident, String password, String registeredBy);

    void associateNewUserToNeighborhood(RegisterUserVO resident, Integer userId);

    void removeUserById(Integer neighborhoodId, Integer userId);

    Integer getUserByEmail(String email);

    List<Resident> getResidentByNeighborhood(Integer neighborhoodId, int active);

}
