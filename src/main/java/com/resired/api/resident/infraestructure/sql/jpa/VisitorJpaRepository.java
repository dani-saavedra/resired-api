package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VisitorJpaRepository extends JpaRepository<VisitorOrm, Integer> {

    @Query(value = "SELECT visi FROM VisitorOrm  visi WHERE visi.authorizingUser.email = ?1 order by visi.createdAt desc")
    List<VisitorOrm> obtainVisitorByEmailResident(String emailResident);
}
