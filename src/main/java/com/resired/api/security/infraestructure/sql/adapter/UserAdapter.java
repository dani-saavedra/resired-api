package com.resired.api.security.infraestructure.sql.adapter;

import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Transactional
public class UserAdapter implements UserPort {

    private UserJpaRepository userJpaRepository;

    @Override
    public User getResidentByCredentials(String email, String password) {
        UserOrm user = userJpaRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            return null;
        }
        User resident = new User(user.getDocumentId(), user.getFirstName(), user.getEmail(), user.getLastName(), user.isActive(),
            user.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .map(UserRolOrm::converToEntity)
                .toList());
        resident.validateMandatoryChangePassword(user.getUpdateDate());
        return resident;
    }

    @Override
    public void changePassword(String documentId, String newEncryptPass) {
        userJpaRepository.updatePassword(documentId, newEncryptPass);
    }
}
