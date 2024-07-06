package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.exception.InvalidPqrsException;
import com.resired.api.admin.application.vo.ResponsePQRS;
import com.resired.api.resident.application.dto.PqrsDetailDTO;
import com.resired.api.resident.application.port.PqrsPort;
import com.resired.api.resident.domain.enums.StatePQRS;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.usecase.PushAppUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminPqrsUseCase {

    private final PqrsPort port;
    private final PushAppUseCase pushAppUseCase;

    public void updateStatePQRS(String ticketNumber, Integer neighbor) {
        PqrsDetailDTO pqrs = port.obtainPqrByTicketNumber(ticketNumber);
        if (pqrs == null || !pqrs.state().equals(StatePQRS.RADICADA)) {
            throw new InvalidPqrsException("PQRS01");
        }
        port.updateStatePQRS(ticketNumber);
        NotificationHomeRequest notificationHome = new NotificationHomeRequest("PQRS " + ticketNumber + " en gestión",
            "La PQRS " + ticketNumber + " comenzo a ser gestionada por la administración", pqrs.homeId());
        pushAppUseCase.notifyHome(notificationHome);
    }

    public void responsePQRS(ResponsePQRS responsePQRS) {
        PqrsDetailDTO pqrs = port.obtainPqrByTicketNumber(responsePQRS.ticketNumber());
        if (pqrs == null || !pqrs.state().equals(StatePQRS.EN_REVISION)) {
            throw new InvalidPqrsException("PQRS01");
        }
        NotificationHomeRequest notificationHome = new NotificationHomeRequest("PQRS " + responsePQRS.ticketNumber() + " completada",
            "La PQRS " + responsePQRS.ticketNumber() + " ha sido completada por la administración, ve a conocer su respuesta", pqrs.homeId());
        port.responsePqrs(responsePQRS.ticketNumber(), responsePQRS.response(), responsePQRS.userResponse());
        pushAppUseCase.notifyHome(notificationHome);
    }

    public List<PqrsDetailDTO> obtainPqrsByStateAndNeighborhood(StatePQRS statePQRS, Integer neighborhood) {
        return port.obtainPqrByStateAndNeighborhood(statePQRS, neighborhood);
    }
}
