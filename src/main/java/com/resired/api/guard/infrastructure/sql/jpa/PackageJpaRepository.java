package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageJpaRepository extends JpaRepository<PackageOrm, Integer> {

    @Query("SELECT package FROM PackageOrm package " +
        " WHERE package.home.block.neighborhoodOrm.id = :neighborhoodId" +
        " ORDER BY package.createdDate DESC")
    List<PackageOrm> findAllByNeighborhoodId(Integer neighborhoodId);

    @Query("SELECT package FROM PackageOrm package " +
        " JOIN HomeOrm home ON package.home = home" +
        " WHERE home.block.neighborhoodOrm.id = :neighborhoodId " +
        " AND package.id = :packageId")
    PackageOrm findByIdAndNeighborhoodId(Integer packageId, Integer neighborhoodId);

    @Query(value = "SELECT p.* FROM package p, home h, block b" +
        " WHERE p.home_id = h.id" +
        " AND h.block = b.id" +
        " AND b.neighborhood_id = :neighborhoodId" +
        " AND p.status = :status" +
        " ORDER BY p.received_date DESC", nativeQuery = true)
    List<PackageOrm> findAllByNeighborhoodIdAndStatus(@Param("neighborhoodId") Integer neighborhoodId,
                                                      @Param("status") String status);

    List<PackageOrm> findByHomeId(Integer homeId);
}
