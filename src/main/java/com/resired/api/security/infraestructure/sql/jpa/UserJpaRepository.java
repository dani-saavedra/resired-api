package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserOrm, UUID> {

    UserOrm findByEmailAndPassword(String email, String password);
    UserOrm findByEmail(String email);
}
