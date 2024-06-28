package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.exception.InvalidPqrsException;
import com.resired.api.admin.application.vo.ResponsePQRS;
import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.application.port.PqrsPort;
import com.resired.api.resident.domain.enums.StatePQRS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminPqrsUseCase {

    private final PqrsPort port;

    public void updateStatePQRS(String ticketNumber, Integer neighbor) {
        Integer neighborByTicketNumber = port.getNeighborByTicketNumber(ticketNumber);
        if (neighborByTicketNumber == null || !neighborByTicketNumber.equals(neighbor)) {
            throw new InvalidPqrsException("PQRS01");
        }
        port.updateStatePQRS(ticketNumber);
    }

    public void responsePQRS(ResponsePQRS responsePQRS) {
        Integer neighborByTicketNumber = port.getNeighborByTicketNumber(responsePQRS.ticketNumber());
        if (neighborByTicketNumber == null || !neighborByTicketNumber.equals(responsePQRS.neighborhood())) {
            throw new InvalidPqrsException("PQRS01");
        }
        port.responsePqrs(responsePQRS.ticketNumber(), responsePQRS.response(), responsePQRS.userResponse());
    }

    public List<PqrsDetailDTO> obtainPqrsByStateAndNeighborhood(StatePQRS statePQRS, Integer neighborhood) {
        return port.obtainPqrByStateAndNeighborhood(statePQRS, neighborhood);
    }
}
