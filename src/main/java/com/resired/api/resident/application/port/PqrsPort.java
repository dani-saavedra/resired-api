package com.resired.api.resident.application.port;

import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.domain.enums.StatePQRS;

import java.util.List;

public interface PqrsPort {

    List<PqrsResponseDTO> obtainPQRSByHomeId(Integer homeId);

    PqrsDetailDTO obtainPqrByTicketNumber(String ticketNumber);

    List<PqrsDetailDTO> obtainPqrByStateAndNeighborhood(StatePQRS statePQRS, Integer neighborhood);

    Integer totalPqrByNeighborhood(Integer neighborhoodId);

    void registerPQRr(RegisterPqrs registerPqrs, String ticketNumber, StatePQRS statePQRS, String url);

    Integer getNeighborByTicketNumber(String ticketNumber);

    void updateStatePQRS(String ticketNumber);

    void responsePqrs(String ticketNumber, String response, Integer userId);
}
