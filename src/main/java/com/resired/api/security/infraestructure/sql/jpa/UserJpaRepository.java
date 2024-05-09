package com.resired.api.security.infraestructure.sql.jpa;

import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserOrm, UUID> {

    UserOrm findByEmailAndPassword(String email, String password);
}
