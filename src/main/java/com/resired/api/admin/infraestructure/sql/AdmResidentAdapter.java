package com.resired.api.admin.infraestructure.sql;

import com.resired.api.admin.domain.vo.RegisterResidentVO;
import com.resired.api.admin.domain.repository.ResidentPort;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.jpa.UserJpaRepository;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AdmResidentAdapter implements ResidentPort {

    public static final int ACTIVE = 1;
    private final UserJpaRepository jpaRepository;

    @Override
    public void registerResident(RegisterResidentVO resident, String registeredBy, String password) {
        ArrayList<UserRolOrm> userRols = new ArrayList<>();
        UserRolOrm rolOrm = getUserRolOrm(resident);

        UserOrm entity = new UserOrm();
        entity.setActive(ACTIVE);
        entity.setRegisteredBy(registeredBy);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setDocumentId(resident.documentId());
        entity.setDocumentType(resident.documentType());
        entity.setEmail(resident.email());
        entity.setPassword(password);
        entity.setFirstName(resident.firstName());
        entity.setLastName(resident.lastName());
        entity.setUserRols(userRols);
        rolOrm.setUser(entity);
        userRols.add(rolOrm);
        jpaRepository.saveAndFlush(entity);
    }

    @Override
    public void associateNewResidence(RegisterResidentVO resident, Integer userId) {
        UserRolOrm rolOrm = getUserRolOrm(resident);
        Optional<UserOrm> userOrm = jpaRepository.findById(userId);
        if (userOrm.isPresent()) {
            rolOrm.setUser(userOrm.get());
            userOrm.get().getUserRols().add(rolOrm);
            jpaRepository.save(userOrm.get());
        }
    }

    @Override
    public void removeResidentByHome(Integer homeId) {
        jpaRepository.removeResidentByHome(homeId, LocalDateTime.now());
    }

    @Override
    public void removeResidentByUserId(Integer neighborhoodId, Integer userId) {
        jpaRepository.removeResidentByUserId(neighborhoodId, userId, LocalDateTime.now());
    }

    private static UserRolOrm getUserRolOrm(RegisterResidentVO resident) {
        NeighborhoodOrm neighborhood = new NeighborhoodOrm();
        neighborhood.setId(resident.neighborhoodId());
        HomeOrm home = new HomeOrm();
        home.setId(resident.homeId());

        UserRolOrm rolOrm = new UserRolOrm();
        rolOrm.setRol(UserType.RESIDENT);
        rolOrm.setActive(ACTIVE);
        rolOrm.setCreatedDate(LocalDateTime.now());
        rolOrm.setNeighborhood(neighborhood);
        rolOrm.setHome(home);
        return rolOrm;
    }

    @Override
    public Integer getResidentIdByEmail(String email) {
        UserOrm user = jpaRepository.findByEmail(email);
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }
}
