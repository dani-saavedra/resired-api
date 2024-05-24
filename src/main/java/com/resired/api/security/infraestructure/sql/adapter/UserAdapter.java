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
        User resident = getUser(user);
        if (resident == null) return null;
        resident.validateMandatoryChangePassword(user.getUpdateDate());
        return resident;
    }

    @Override
    public void changePassword(String documentId, String newEncryptPass) {
        userJpaRepository.updatePassword(documentId, newEncryptPass);
    }

    @Override
    public User getResidentByDocument(String documentId) {
        UserOrm userOrm = userJpaRepository.findByDocumentId(documentId);
        return getUser(userOrm);
    }

    @Override
    public User getResidentByEmail(String email) {
        UserOrm user = userJpaRepository.findByEmail(email);
        return getUser(user);
    }

    private User getUser(UserOrm userOrm) {
        if (userOrm == null) {
            return null;
        }
        return new User(userOrm.getId(), userOrm.getDocumentId(), userOrm.getFirstName(), userOrm.getEmail(),
            userOrm.getLastName(), userOrm.isActive(),
            userOrm.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .map(UserRolOrm::converToEntity)
                .toList());
    }
}
