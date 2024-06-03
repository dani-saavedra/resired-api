package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

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

}
