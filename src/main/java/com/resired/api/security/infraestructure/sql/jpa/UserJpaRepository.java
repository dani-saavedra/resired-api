package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserOrm, UUID> {

    UserOrm findByEmailAndPassword(String email, String password);
    UserOrm findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update UserOrm userApp set userApp.password =:newPassword where userApp.documentId =:documentId")
    void updatePassword(@Param("documentId") String documentId, @Param("newPassword") String newPassword);
}
