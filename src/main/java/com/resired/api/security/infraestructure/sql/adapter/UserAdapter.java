package com.resired.api.security.infraestructure.sql.adapter;

import com.resired.api.security.domain.entity.Resident;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserAdapter implements UserPort {

    private UserJpaRepository userJpaRepository;

    @Override
    public Resident getResidentByCredentials(String email, String password) {
        UserOrm user = userJpaRepository.findByEmailAndPassword(email, password);
        Resident resident = new Resident(user.getDocumentId(), user.getFirstName(), user.getLastName(), user.isActive());
        resident.validateMandatoryChangePassword(user.getUpdateDate());
        return resident;
    }
}
