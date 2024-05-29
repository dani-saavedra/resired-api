package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageJpaRepository extends JpaRepository<PackageOrm, Integer> {
}
