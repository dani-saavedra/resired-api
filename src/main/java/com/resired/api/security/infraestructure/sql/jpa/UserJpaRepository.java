package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.BlockOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserOrm, Integer> {

    UserOrm findByEmailAndPassword(String email, String password);

    UserOrm findByEmail(String email);

    @Modifying
    @Query("update UserOrm userApp set userApp.password =:newPassword, userApp.updateDate =:updated where userApp.documentId =:documentId")
    void updatePassword(@Param("documentId") String documentId, @Param("newPassword") String newPassword,
                        @Param("updated") LocalDateTime updated);

    @Modifying
    @Query("update UserOrm userApp set userApp.password =:newPassword, userApp.updateDate =:updated where userApp.id =:userId")
    void updatePassword(@Param("userId") Integer userId, @Param("newPassword") String newPassword,
                        @Param("updated") LocalDateTime updated);

    @Modifying
    @Query("update UserRolOrm userRol set userRol.active =0, userRol.updateDate =:now where userRol.home.id =:homeId")
    void removeResidentByHome(Integer homeId, LocalDateTime now);

    @Modifying
    @Query("update UserRolOrm userRol set userRol.active =0, userRol.updateDate =:now where userRol.user.id =:userId" +
        " and userRol.neighborhood.id =:neighborhood")
    void removeResidentByUserId(Integer neighborhood, Integer userId, LocalDateTime now);

    UserOrm findByDocumentId(String documentId);

    @Query("SELECT ur.neighborhood FROM UserRolOrm ur " +
        "JOIN ur.user u " +
        "WHERE u.email = :email " +
        "AND ur.rol = :rol " +
        "AND ur.active = 1")
    List<NeighborhoodOrm> findNeighborhoodsByUserEmailAndUserRole(@Param("email") String email, @Param("rol") UserType rol);

    @Query("SELECT ur.user FROM UserRolOrm ur WHERE ur.home.id = :homeId AND ur.rol = 'RESIDENT' AND ur.active = 1")
    List<UserOrm> findResidentsByHomeId(@Param("homeId") Integer homeId);

    @Query("SELECT ur.user FROM UserRolOrm ur WHERE ur.neighborhood.id = :neighborhoodId AND ur.rol = 'RESIDENT' AND ur.active = 1")
    List<UserOrm> findResidentsByNeighborhoodId(@Param("neighborhoodId") Integer neighborhoodId);

    @Query("SELECT role FROM UserRolOrm role JOIN role.user user" +
        " WHERE role.neighborhood.id = :neighborhoodId AND role.rol = :role AND role.active = :active")
    List<UserRolOrm> findAllByNeighborhoodIdAndRolAndActive(@Param("neighborhoodId") Integer neighborhoodId, @Param("role") UserType role, @Param("active") int active);

    @Query("SELECT u.home.block FROM UserRolOrm u WHERE u.rol = 'RESIDENT' AND u.active = 1 AND u.user.id = :userId")
    List<BlockOrm> findBlockOrmsByResidentId(@Param("userId") Integer userId);

}
