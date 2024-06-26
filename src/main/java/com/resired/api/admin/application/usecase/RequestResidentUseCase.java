package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.repository.ResidentRequestPort;
import com.resired.api.admin.domain.vo.DecisionRequestResident;
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

    public List<ResidentRequestDto> obtainRequestResident(Integer neighborhood, DecisionRequestResident decision) {
        return port.obtainRequestResident(neighborhood, decision);
    }

    public void rejectRequestResident(Integer id, Integer neighborhood) {
        boolean rejectionCompleted = port.rejectRequestResident(id, neighborhood);
        if (!rejectionCompleted) {
            log.error("Rejection id: {} in neighborhood: {} no exists", id, neighborhood);
        }
    }
}
