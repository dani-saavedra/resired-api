package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.application.port.PqrsPort;
import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.resident.infraestructure.sql.jpa.PqrsJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.PqrsOrm;
import com.resired.api.utils.FormatDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PQRSAdapter implements PqrsPort {

    private final PqrsJpaRepository jpaRepository;

    @Override
    public List<PqrsResponseDTO> obtainPQRSByResidentId(Integer residentId) {
        return jpaRepository.findByResidentId(residentId).stream().map(orm ->
            new PqrsResponseDTO(FormatDate.formatDate(orm.getCreationDate()), orm.getTitle(), orm.getCategory(),
                orm.getTicketNumber(), orm.getState())).toList();
    }

    @Override
    public Integer totalPqrByNeighborhood(Integer neighborhoodId) {
        return jpaRepository.countByNeighborhoodId(neighborhoodId);
    }

    @Override
    public void registerPQRr(RegisterPqrs registerPqrs, String ticketNumber, StatePQRS state) {
        PqrsOrm orm = new PqrsOrm(registerPqrs.title(), registerPqrs.description(), ticketNumber,
            registerPqrs.category(), state, registerPqrs.residentId(), registerPqrs.neighbor());
        jpaRepository.save(orm);
    }

}

