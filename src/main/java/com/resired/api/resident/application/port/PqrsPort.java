package com.resired.api.resident.application.port;

import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.domain.enums.StatePQRS;

import java.util.List;

public interface PqrsPort {

    List<PqrsResponseDTO> obtainPQRSByResidentId(Integer residentId);

    Integer totalPqrByNeighborhood(Integer neighborhoodId);

    void registerPQRr(RegisterPqrs registerPqrs, String ticketNumber, StatePQRS statePQRS);

    Integer getNeighborByTicketNumber(String ticketNumber);

    void updateStatePQRS(String ticketNumber);
}
