package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageJpaRepository extends JpaRepository<PackageOrm, Integer> {
    @Query("SELECT package FROM PackageOrm package " +
        " JOIN HomeOrm home ON package.home = home.id" +
        " WHERE home.neighborhoodId = :neighborhoodId " +
        " ORDER BY package.createdDate DESC")
    List<PackageOrm> findAllByNeighborhoodId(Integer neighborhoodId);

    @Query("SELECT package FROM PackageOrm package " +
        " JOIN HomeOrm home ON package.home = home.id" +
        " WHERE home.neighborhoodId = :neighborhoodId " +
        " AND package.id = :packageId")
    PackageOrm findByIdAndNeighborhoodId(Integer packageId, Integer neighborhoodId);
}
