package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.repository.ResidentRequestPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestResidentUseCase {

    private final ResidentRequestPort port;

    public void registerRequestResident(ResidentRequestDto residentRequestDto) {
        port.registerRequestResident(residentRequestDto);
    }

    public List<ResidentRequestDto> obtainRequestResident(Integer neighborhood) {
        return port.obtainRequestResident(neighborhood);
    }
}
