package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import com.resired.api.resident.infraestructure.sql.orm.VisitorOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VisitorJpaRepository extends JpaRepository<VisitorOrm, Integer> {

    @Query(value = "SELECT visi FROM VisitorOrm  visi WHERE visi.authorizingUser.email = ?1 and visi.deleted=false order by visi.favorite, visi.createdAt desc")
    List<VisitorOrm> obtainVisitorByEmailResident(String emailResident);

    @Query(value = "SELECT visi FROM VisitorOrm  visi WHERE visi.authorizingUser.email = ?1 and visi.deleted=false and visi.document = ?2" +
        " order by visi.createdAt desc")
    VisitorOrm obtainVisitorByEmailResidentAndDocument(String emailResident, String document);

    @Query(value = "SELECT qr FROM QrOrm  qr WHERE qr.visitor.authorizingUser.email = :emailResident and qr.available=true and" +
        " qr.visitor.deleted=false and qr.visitor.document=:document")
    QrOrm obtainQRByEmailResidentAndDocumentVisitor(String emailResident, String document);

    @Modifying
    @Query("update VisitorOrm visitor set visitor.deleted = true where visitor.authorizingUser.id =:userId and visitor.document =:visitorDocument")
    void deleteVisitorByUserId(Integer userId, String visitorDocument);
}
