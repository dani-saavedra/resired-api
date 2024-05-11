package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.guard.infrastructure.sql.orm.PacketOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacketJpaRepository extends JpaRepository<PacketOrm, Long> {
}
