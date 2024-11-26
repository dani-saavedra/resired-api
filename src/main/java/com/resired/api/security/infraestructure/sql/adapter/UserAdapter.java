package com.resired.api.security.infraestructure.sql.adapter;

import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@AllArgsConstructor
@Transactional
public class UserAdapter implements UserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getUserByCredentials(String email, String password) {
        UserOrm userOrm = userJpaRepository.findByEmailAndPassword(email, password);
        if (userOrm == null || userOrm.getUserRols().isEmpty()) {
            return null;
        }
        User user = new User(userOrm.getId(), userOrm.getDocumentId(), userOrm.getFirstName(), userOrm.getEmail(),
            userOrm.getLastName(), userOrm.isActive(),
            userOrm.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .map(UserRolOrm::converToEntity)
                .toList());
        user.validateMandatoryChangePassword(userOrm.getUpdateDate());
        return user;
    }

    @Override
    public void changePassword(String documentId, String newEncryptPass) {
        userJpaRepository.updatePassword(documentId, newEncryptPass, LocalDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public void changePassword(Integer userId, String newEncryptPass) {
        userJpaRepository.updatePassword(userId, newEncryptPass, LocalDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public User getUserByEmailAndType(String email, UserType roleFilter) {
        UserOrm userOrm = userJpaRepository.findByEmail(email);
        if (userOrm == null) {
            return null;
        }
        return new User(userOrm.getId(), userOrm.getDocumentId(), userOrm.getFirstName(), userOrm.getEmail(),
            userOrm.getLastName(), userOrm.isActive(),
            userOrm.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .filter(userRolOrm -> roleFilter.equals(userRolOrm.getRol()))
                .map(UserRolOrm::converToEntity)
                .toList());
    }

    @Override
    public List<User> findResidentsByHomeId(Integer homeId) {
        List<UserOrm> residents = userJpaRepository.findResidentsByHomeId(homeId);
        return residents.stream()
            .map(UserOrm::toEntity)
            .toList();
    }

    @Override
    public User getUserById(Integer userId) {
        UserOrm userOrm = userJpaRepository.findById(userId).orElse(null);
        if (userOrm == null) {
            return null;
        }
        return userOrm.toEntity();
    }


}
