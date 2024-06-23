package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.BlockOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockJpaRepository extends JpaRepository<BlockOrm, Integer> {
    Optional<BlockOrm> findByNameAndNeighborhoodOrmId(String name, Integer neighborhoodId);
}
