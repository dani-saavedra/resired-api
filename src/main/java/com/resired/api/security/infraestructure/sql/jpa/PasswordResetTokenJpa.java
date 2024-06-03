package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.security.infraestructure.sql.orm.PasswordResetTokenOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenJpa extends JpaRepository<PasswordResetTokenOrm, Integer> {

    PasswordResetTokenOrm findByToken(String token);

    @Modifying
    @Query("update PasswordResetTokenOrm passToken set passToken.invalid = true where passToken.token =?1")
    void updateToken(String token);
}
