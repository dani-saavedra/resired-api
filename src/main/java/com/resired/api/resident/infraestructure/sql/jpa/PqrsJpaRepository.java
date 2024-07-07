package com.resired.api.resident.infraestructure.sql.jpa;

import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.resident.infraestructure.sql.orm.PqrsOrm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PqrsJpaRepository extends JpaRepository<PqrsOrm, Integer> {

    List<PqrsOrm> findByHomeId(Integer homeId);

    List<PqrsOrm> findByStateAndNeighborhoodId(StatePQRS state, Integer neighborhoodId);

    Integer countByNeighborhoodId(Integer neighborhoodId);

    PqrsOrm findByTicketNumber(String ticketNumber);
}
