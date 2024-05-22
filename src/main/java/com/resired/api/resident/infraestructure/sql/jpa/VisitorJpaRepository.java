package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorJpaRepository extends JpaRepository<VisitorOrm, Integer> {

}
