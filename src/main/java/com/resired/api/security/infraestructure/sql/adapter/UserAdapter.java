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
import java.util.List;

@Repository
@AllArgsConstructor
@Transactional
public class UserAdapter implements UserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getUserByCredentials(String email, String password) {
        UserOrm userOrm = userJpaRepository.findByEmailAndPassword(email, password);
        if (userOrm == null) {
            return null;
        }
        User resident = new User(userOrm.getId(), userOrm.getDocumentId(), userOrm.getFirstName(), userOrm.getEmail(),
            userOrm.getLastName(), userOrm.isActive(),
            userOrm.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .map(UserRolOrm::converToEntityGeral)
                .toList());
        resident.validateMandatoryChangePassword(userOrm.getUpdateDate());
        return resident;
    }

    @Override
    public void changePassword(String documentId, String newEncryptPass) {
        userJpaRepository.updatePassword(documentId, newEncryptPass, LocalDateTime.now());
    }

    @Override
    public void changePassword(Integer userId, String newEncryptPass) {
        userJpaRepository.updatePassword(userId, newEncryptPass, LocalDateTime.now());
    }


    @Override
    public User getResidentByEmail(String email) {
        UserOrm residentOrm = userJpaRepository.findByEmail(email);
        if (residentOrm == null) {
            return null;
        }
        return new User(residentOrm.getId(), residentOrm.getDocumentId(), residentOrm.getFirstName(), residentOrm.getEmail(),
            residentOrm.getLastName(), residentOrm.isActive(),
            residentOrm.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .filter(userRolOrm -> userRolOrm.getRol().equals(UserType.RESIDENT))
                .map(UserRolOrm::converToEntity)
                .toList());
    }

    @Override
    public User getGuardByEmail(String email) {
        UserOrm guardOrm = userJpaRepository.findByEmail(email);
        if (guardOrm == null) {
            return null;
        }
        return new User(guardOrm.getId(), guardOrm.getDocumentId(), guardOrm.getFirstName(), guardOrm.getEmail(),
            guardOrm.getLastName(), guardOrm.isActive(),
            guardOrm.getUserRols().stream()
                .filter(UserRolOrm::isActive)
                .filter(userRolOrm -> userRolOrm.getRol().equals(UserType.GUARD))
                .map(UserRolOrm::converToEntityGeral)
                .toList());
    }

    @Override
    public List<User> findResidentsByHomeId(Integer homeId) {
        List<UserOrm> residents = userJpaRepository.findResidentsByHomeId(homeId);
        return residents.stream()
            .map(UserOrm::toEntity)
            .toList();
    }


}
