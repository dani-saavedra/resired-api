package com.resired.api.admin.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.BlockOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockJpaRepository extends JpaRepository<BlockOrm, Integer> {
    Optional<BlockOrm> findByNameAndNeighborhoodOrmId(String name, Integer neighborhoodId);
}
