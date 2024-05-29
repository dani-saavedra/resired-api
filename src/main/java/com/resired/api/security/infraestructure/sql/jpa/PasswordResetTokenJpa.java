package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.security.infraestructure.sql.orm.PasswordResetTokenOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenJpa extends JpaRepository<PasswordResetTokenOrm, Integer> {
}
