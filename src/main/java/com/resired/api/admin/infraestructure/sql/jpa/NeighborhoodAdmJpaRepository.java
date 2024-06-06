package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeighborhoodAdmJpaRepository extends JpaRepository<NeighborhoodOrm, Integer> {


}
