package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.admin.infraestructure.sql.orm.ResidentRequestOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRequestJpaRepository extends JpaRepository<ResidentRequestOrm, Integer> {


}
