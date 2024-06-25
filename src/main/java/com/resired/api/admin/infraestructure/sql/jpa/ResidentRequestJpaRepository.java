package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.admin.infraestructure.sql.orm.ResidentRequestOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentRequestJpaRepository extends JpaRepository<ResidentRequestOrm, Integer> {

    List<ResidentRequestOrm> findResidentRequestOrmByNeighborhood(Integer neighborhood);
}
