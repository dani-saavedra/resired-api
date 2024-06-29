package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.application.dto.GuardDto;
import com.resired.api.admin.application.repository.AdminGuardPort;
import com.resired.api.admin.domain.entity.Resident;
import com.resired.api.admin.domain.entity.ResidentHome;
import com.resired.api.admin.domain.repository.AdminResidentPort;
import com.resired.api.admin.domain.repository.AdminUserPort;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AdmAdminUserAdapter implements AdminUserPort, AdminResidentPort, AdminGuardPort {

    public static final int ACTIVE = 1;
    private final UserJpaRepository jpaRepository;

    @Override
    public void registerUserToNeighborhood(RegisterUserVO user, String registeredBy, String password) {
        ArrayList<UserRolOrm> userRols = new ArrayList<>();
        UserRolOrm rolOrm = getUserRolOrm(user);

        UserOrm entity = new UserOrm();
        entity.setActive(ACTIVE);
        entity.setRegisteredBy(registeredBy);
        entity.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        entity.setDocumentId(user.documentId());
        entity.setDocumentType(user.documentType());
        entity.setEmail(user.email());
        entity.setPassword(password);
        entity.setFirstName(user.firstName());
        entity.setLastName(user.lastName());
        entity.setUserRols(userRols);
        rolOrm.setUser(entity);
        userRols.add(rolOrm);
        jpaRepository.saveAndFlush(entity);
    }

    @Override
    public void associateNewUserToNeighborhood(RegisterUserVO resident, Integer userId) {
        UserRolOrm rolOrm = getUserRolOrm(resident);
        Optional<UserOrm> userOrm = jpaRepository.findById(userId);
        if (userOrm.isPresent()) {
            rolOrm.setUser(userOrm.get());
            userOrm.get().getUserRols().add(rolOrm);
            jpaRepository.save(userOrm.get());
        }
    }

    @Override
    public void removeUserByHome(Integer homeId) {
        jpaRepository.removeResidentByHome(homeId, LocalDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public void removeUserById(Integer neighborhoodId, Integer userId) {
        jpaRepository.removeResidentByUserId(neighborhoodId, userId, LocalDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public Integer getUserByEmail(String email) {
        UserOrm user = jpaRepository.findByEmail(email);
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }

    @Override
    public List<Resident> getResidentByNeighborhood(Integer neighborhoodId, int active) {
        List<UserRolOrm> residents = jpaRepository.findAllByNeighborhoodIdAndRolAndActive(neighborhoodId, UserType.RESIDENT, active);
        return residents.stream().map(role -> new Resident(
                role.getUser().getDocumentId(),
                role.getUser().getDocumentType().getValue(),
                role.getUser().getLastName(),
                role.getUser().getFirstName(),
                role.getUser().getEmail(),
                role.getHome().getNumber(),
                role.getUser().getId()))
            .toList();
    }

    @Override
    public List<ResidentHome> getResidentByHome(Integer homeId) {
        return jpaRepository.findResidentsByHomeId(homeId).stream().map(orm -> new ResidentHome(
                orm.getDocumentId(),
                orm.getDocumentType().getValue(),
                orm.getLastName(),
                orm.getFirstName(),
                orm.getEmail(),
                orm.getId()))
            .toList();
    }

    @Override
    public List<GuardDto> getAllGuards(Integer neighborhoodId, int active) {
        List<UserRolOrm> guards = jpaRepository.findAllByNeighborhoodIdAndRolAndActive(neighborhoodId, UserType.GUARD, active);
        return guards.stream().map(guard -> new GuardDto(
                guard.getUser().getDocumentId(),
                guard.getUser().getDocumentType().getValue(),
                guard.getUser().getLastName(),
                guard.getUser().getFirstName(),
                guard.getUser().getEmail(),
                guard.getUser().getId()))
            .toList();
    }

    private static UserRolOrm getUserRolOrm(RegisterUserVO user) {
        UserRolOrm rolOrm = new UserRolOrm();
        NeighborhoodOrm neighborhood = new NeighborhoodOrm();
        neighborhood.setId(user.neighborhoodId());
        if (user.homeId() != null) {
            HomeOrm home = new HomeOrm();
            home.setId(user.homeId());
            rolOrm.setHome(home);
        }
        rolOrm.setRol(user.userType());
        rolOrm.setActive(ACTIVE);
        rolOrm.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        rolOrm.setNeighborhood(neighborhood);
        return rolOrm;
    }
}
