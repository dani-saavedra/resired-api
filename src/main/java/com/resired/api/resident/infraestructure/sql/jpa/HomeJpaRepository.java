package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeJpaRepository extends JpaRepository<HomeOrm, Integer> {

    @Query(value = "SELECT pac FROM PackageOrm  pac WHERE pac.home = ?1 order by pac.createdDate asc")
    List<PackageOrm> getPackages(Integer homeId);

    @Query("SELECT home.id FROM HomeOrm home WHERE home.block = :block AND home.number = :number")
    Integer findHomeIdByBlockAndNumber(String block, String number);

    @Query("SELECT home.id FROM HomeOrm home WHERE home.block IS NULL AND home.number = :homeNumber")
    Integer findHomeIdByNumber(String homeNumber);
}
