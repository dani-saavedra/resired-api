package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.application.port.PqrsPort;
import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.resident.infraestructure.sql.jpa.PqrsJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.PqrsOrm;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.utils.FormatDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@AllArgsConstructor
public class PQRSAdapter implements PqrsPort {

    private final PqrsJpaRepository jpaRepository;

    @Override
    public List<PqrsResponseDTO> obtainPQRSByHomeId(Integer homeId) {
        return jpaRepository.findByHomeId(homeId)
            .stream()
            .map(orm ->
                new PqrsResponseDTO(FormatDate.formatDate(orm.getCreationDate()),
                    FormatDate.formatDate(orm.getResponseDate()), orm.getTitle(), orm.getCategory(),
                    orm.getTicketNumber(), orm.getState(), orm.getDetails())).toList();
    }

    @Override
    public PqrsDetailDTO obtainPqrByTicketNumber(String ticketNumber) {
        PqrsOrm pqrOrm = jpaRepository.findByTicketNumber(ticketNumber);
        if (pqrOrm == null) {
            return null;
        }
        return getPqrsDetailDTO(pqrOrm);
    }

    @Override

    public List<PqrsDetailDTO> obtainPqrByStateAndNeighborhood(StatePQRS statePQRS, Integer neighborhood) {
        return jpaRepository.findByStateAndNeighborhoodIdOrderByCreationDateAscResidentAsc(statePQRS, neighborhood)
            .stream()
            .map(PQRSAdapter::getPqrsDetailDTO)
            .toList();
    }

    @Override
    public Integer totalPqrByNeighborhood(Integer neighborhoodId) {
        return jpaRepository.countByNeighborhoodId(neighborhoodId);
    }

    @Override
    public void registerPQRr(RegisterPqrs registerPqrs, String ticketNumber, StatePQRS state, String details) {
        PqrsOrm orm = new PqrsOrm(registerPqrs.title(), registerPqrs.description(), ticketNumber, registerPqrs.category(),
            state, registerPqrs.residentId(), registerPqrs.neighbor(), registerPqrs.homeId(), details);
        jpaRepository.save(orm);
    }

    @Override
    public Integer getNeighborByTicketNumber(String ticketNumber) {
        PqrsOrm pqrsOrm = jpaRepository.findByTicketNumber(ticketNumber);
        if (pqrsOrm == null) {
            return null;
        }
        return pqrsOrm.getNeighborhood().getId();
    }

    @Override
    public void updateStatePQRS(String ticketNumber) {
        PqrsOrm pqrsOrm = jpaRepository.findByTicketNumber(ticketNumber);
        pqrsOrm.setState(StatePQRS.EN_REVISION);

        jpaRepository.save(pqrsOrm);
    }

    @Override
    public void responsePqrs(String ticketNumber, String response, Integer userId) {
        PqrsOrm pqrsOrm = jpaRepository.findByTicketNumber(ticketNumber);
        pqrsOrm.setState(StatePQRS.COMPLETADA);
        pqrsOrm.setResponse(response);
        pqrsOrm.setAdminResponds(new UserOrm(userId));
        pqrsOrm.setResponseDate(LocalDateTime.now(ZoneOffset.UTC));
        jpaRepository.save(pqrsOrm);
    }

    private static PqrsDetailDTO getPqrsDetailDTO(PqrsOrm pqrOrm) {
        String responder = null;
        if (pqrOrm.getAdminResponds() != null) {
            responder = pqrOrm.getAdminResponds().getFirstName();
        }
        return new PqrsDetailDTO(FormatDate.formatDate(pqrOrm.getCreationDate()),
            FormatDate.formatDate(pqrOrm.getResponseDate()), pqrOrm.getTitle(), pqrOrm.getCategory(),
            pqrOrm.getTicketNumber(), pqrOrm.getState(), responder, pqrOrm.getResponse(), pqrOrm.getDescription(),
            pqrOrm.getResident().fullName(), pqrOrm.getHome().getFullHomeName(), pqrOrm.getHome().getId());
    }

}

