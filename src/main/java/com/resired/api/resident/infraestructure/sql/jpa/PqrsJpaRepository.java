package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.infraestructure.sql.orm.PqrsOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PqrsJpaRepository extends JpaRepository<PqrsOrm, Integer> {

    List<PqrsOrm> findByResidentId(Integer residentId);

    Integer countByNeighborhoodId(Integer neighborhoodId);

    PqrsOrm findByTicketNumber(String ticketNumber);
}
