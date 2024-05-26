package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.QrOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrJpaRepository extends JpaRepository<QrOrm, Integer> {

    QrOrm findByQr(String qr);
}
