package com.resired.api.resident.application.usecase;

import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.dto.RegisterPqrs;
import com.resired.api.resident.application.port.PqrsPort;
import com.resired.api.resident.domain.enums.CategoryPQRS;
import com.resired.api.resident.domain.enums.StatePQRS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PqrsUseCase {

    private final PqrsPort port;

    public List<PqrsResponseDTO> obtainPQRSByResident(Integer residentId) {
        return port.obtainPQRSByResidentId(residentId);
    }

    public String registerPQRSByResident(RegisterPqrs registerPqrs) {
        String ticketNumber = generateTicketNumber(registerPqrs.category(), registerPqrs.neighbor());
        port.registerPQRr(registerPqrs, ticketNumber, StatePQRS.RADICADA);
        return ticketNumber;
    }

    private String generateTicketNumber(CategoryPQRS category, Integer neighborhood) {
        Integer totalPqrs = port.totalPqrByNeighborhood(neighborhood);
        String format = String.format("%06d", totalPqrs + 1);
        return category.getCode() + format;
    }
}
