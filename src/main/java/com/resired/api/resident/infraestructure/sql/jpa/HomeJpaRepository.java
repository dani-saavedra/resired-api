package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.PackageOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface HomeJpaRepository extends JpaRepository<HomeOrm, Integer> {

    @Query(value = "SELECT pac FROM PackageOrm  pac WHERE pac.home = ?1 order by pac.updateDate desc, pac.createdDate desc")
    List<PackageOrm> getPackages(Integer homeId);

    List<HomeOrm> findByBlockIdOrderByNumberAsc(Integer blockId);

    List<HomeOrm> findByBlockNeighborhoodOrmIdOrderByNumberAsc(Integer neighborhoodId);

    @Modifying
    @Query("update HomeOrm home set home.number = :name, home.squareMeter =:squareMeter where home.id =:homeId ")
    void updateHome(String name, BigDecimal squareMeter, Integer homeId);
}
