package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageJpaRepository extends JpaRepository<PackageOrm, Integer> {
    @Query("SELECT p FROM PackageOrm p JOIN HomeOrm h ON p.home = h.id WHERE h.neighborhoodId = :neighborhoodId")
    List<PackageOrm> findAllByNeighborhoodId(Integer neighborhoodId);
}
