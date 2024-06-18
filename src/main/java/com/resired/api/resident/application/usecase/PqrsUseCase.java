package com.resired.api.resident.application.usecase;

import com.resired.api.resident.application.dto.PqrsResponseDTO;
import com.resired.api.resident.application.port.PqrsPort;
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
}
