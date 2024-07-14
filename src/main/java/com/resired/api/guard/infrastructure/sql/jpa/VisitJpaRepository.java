package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitJpaRepository extends JpaRepository<VisitOrm, Integer> {
    @Query("SELECT visit FROM VisitOrm visit " +
        "JOIN visit.qr qr " +
        "JOIN qr.visitor visitor " +
        "JOIN visitor.authorizingHome home " +
        "WHERE home.block.neighborhoodOrm.id = :neighborhoodId " +
        "AND visit.checkIn BETWEEN :startDate AND :endDate " +
        "ORDER BY visit.checkIn DESC")
    List<VisitOrm> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                       LocalDateTime startDate, LocalDateTime endDate);
}
