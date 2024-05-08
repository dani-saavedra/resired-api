package com.resired.api.security.domain.repository;

import com.resired.api.security.domain.entity.Resident;

public interface UserPort {

    Resident getResidentByCredentials(String email, String password);
}
