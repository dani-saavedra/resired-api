package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.vo.RegisterUserVO;

public interface AdminUserPort {

    void registerUserToNeighborhood(RegisterUserVO resident, String password, String registeredBy);

    void associateNewUserToNeighborhood(RegisterUserVO resident, Integer userId);

    void removeUserById(Integer neighborhoodId, Integer userId);

    Integer getUserByEmail(String email);
}
