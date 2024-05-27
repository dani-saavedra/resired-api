package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VisitorJpaRepository extends JpaRepository<VisitorOrm, Integer> {

    @Query(value = "SELECT visi FROM VisitorOrm  visi WHERE visi.authorizingUser.email = ?1 and visi.deleted=0 order by visi.favorite, visi.createdAt desc")
    List<VisitorOrm> obtainVisitorByEmailResident(String emailResident);

    @Query(value = "SELECT visi FROM VisitorOrm  visi WHERE visi.authorizingUser.email = ?1 and visi.deleted=0 and visi.document = ?2" +
            " order by visi.createdAt desc")
    VisitorOrm obtainVisitorByEmailResidentAndDocument(String emailResident, String document);
}
