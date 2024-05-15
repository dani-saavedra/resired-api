package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeJpaRepository extends JpaRepository<HomeOrm, Long> {

    @Query(value = "SELECT pac FROM PackageOrm  pac WHERE pac.home.id = ?1 order by pac.createdDate asc")
    List<PackageOrm> getPackages(Long homeId);
}
