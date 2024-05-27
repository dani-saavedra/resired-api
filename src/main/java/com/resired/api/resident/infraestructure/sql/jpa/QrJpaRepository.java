package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface QrJpaRepository extends JpaRepository<QrOrm, Integer> {

    QrOrm findByQr(String qr);

    @Modifying(clearAutomatically = true)
    @Query("update QrOrm qr set qr.available = false, qr.disabledAt = ?1  where qr.visitor.id =?2 and qr.available=true ")
    void disableVisitorQrByIdVisitor(LocalDateTime disabledAt, Integer idVisitor);
}
