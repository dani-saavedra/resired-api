package com.resired.api.security.infraestructure.sql.adapter;

import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserAdapter implements UserPort {

    private UserJpaRepository userJpaRepository;

    @Override
    public User getResidentByCredentials(String email, String password) {
        UserOrm user = userJpaRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            return null;
        }
        List<UserRolOrm> userRols = user.getUserRols();
        UserRolOrm userRolOrm = userRols.get(0);
        User resident = new User(user.getDocumentId(), user.getFirstName(), user.getLastName(), user.isActive());
        resident.validateMandatoryChangePassword(user.getUpdateDate());
        return resident;
    }
}
