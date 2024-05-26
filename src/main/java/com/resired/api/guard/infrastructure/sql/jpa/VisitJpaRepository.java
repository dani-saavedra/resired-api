package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitJpaRepository extends JpaRepository<VisitOrm, Integer> {
}
