package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.ResponsePQRS;
import com.resired.api.admin.application.exception.InvalidPqrsException;
import com.resired.api.resident.application.port.PqrsPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
