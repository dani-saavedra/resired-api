package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.repository.ResidentRequestPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RequestResidentUseCase {

    private final ResidentRequestPort port;

    public void registerRequestResident(ResidentRequestDto residentRequestDto) {
        port.registerRequestResident(residentRequestDto);
    }

    public List<ResidentRequestDto> obtainRequestResident(Integer neighborhood) {
        return port.obtainRequestResident(neighborhood);
    }

    public void rejectRequestResident(Integer id, Integer neighborhood) {
        boolean rejectionCompleted = port.rejectRequestResident(id, neighborhood);
        if (!rejectionCompleted) {
            log.error("Rejection id: {} in neighborhood: {} no exists", id, neighborhood);
        }
    }
}
