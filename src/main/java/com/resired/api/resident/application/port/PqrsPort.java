package com.resired.api.resident.application.port;

import com.resired.api.resident.application.dto.PqrsResponseDTO;

import java.util.List;

public interface PqrsPort {

    List<PqrsResponseDTO> obtainPQRSByResidentId(Integer residentId);
}
