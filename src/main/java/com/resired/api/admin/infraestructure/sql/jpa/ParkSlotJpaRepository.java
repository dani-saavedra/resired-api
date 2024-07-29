package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.admin.infraestructure.sql.orm.ParkSlotOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkSlotJpaRepository extends JpaRepository<ParkSlotOrm, Integer> {
}
