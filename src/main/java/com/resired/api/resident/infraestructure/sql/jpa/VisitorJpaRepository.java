package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VisitorJpaRepository extends JpaRepository<VisitorOrm, Integer> {

    @Query(value = "SELECT visi FROM VisitorOrm  visi WHERE visi.authorizingUser.email = ?1 and visi.deleted=false order by visi.createdAt desc")
    List<VisitorOrm> obtainVisitorByEmailResident(String emailResident);

    @Query(value = "SELECT qr FROM QrOrm  qr WHERE qr.visitor.id = :idVisitor and qr.available=true and" +
            " qr.visitor.deleted=false")
    QrOrm obtainQRByIdVisitor(Integer idVisitor);

    @Modifying
    @Query("update VisitorOrm visitor set visitor.deleted = true where visitor.id =:idVisitor")
    void deleteVisitorById(Integer idVisitor);
}
