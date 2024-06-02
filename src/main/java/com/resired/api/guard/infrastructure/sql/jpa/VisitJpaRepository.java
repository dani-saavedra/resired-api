package com.resired.api.guard.infrastructure.sql.jpa;

import com.resired.api.guard.domain.entity.Visit;
import com.resired.api.guard.infrastructure.sql.orm.VisitOrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitJpaRepository extends JpaRepository<VisitOrm, Integer> {
    @Query("SELECT new com.resired.api.guard.domain.entity.Visit(" +
        "visit.id, qr.visitor.name, qr.visitor.document, home.number, visit.checkIn) " +
        "FROM VisitOrm visit " +
        "JOIN visit.qr qr " +
        "JOIN qr.visitor visitor " +
        "JOIN visitor.authorizingHome home " +
        "WHERE home.neighborhoodId = :neighborhoodId " +
        "AND visit.checkIn BETWEEN :startDate AND :endDate " +
        "ORDER BY visit.checkIn DESC")
    List<Visit> findVisitsByNeighborhoodIdAndDateRange(Integer neighborhoodId,
                                                       LocalDateTime startDate, LocalDateTime endDate);
}
