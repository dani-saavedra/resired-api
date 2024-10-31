package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.repository.ResidentRequestPort;
import com.resired.api.admin.domain.vo.DecisionRequestResident;
import com.resired.api.admin.domain.vo.RegisterUserVO;
import com.resired.api.security.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RequestResidentUseCase {

    private final ResidentRequestPort port;
    private final AdminUserUseCase userUseCase;

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

    public void acceptRequestResident(Integer idRequest, Integer idHome, Integer neighborhood, String emailAdmin)
        throws GeneralSecurityException {
        ResidentRequestDto request = port.obtainRequestResidentsById(idRequest);

        RegisterUserVO registerUserVO = new RegisterUserVO(request.document(), request.documentType(),
            request.firstName(), request.lastName(), request.email(), neighborhood, idHome,
            UserType.RESIDENT);
        userUseCase.registerUserToNeighborhood(registerUserVO, emailAdmin, false);
        boolean acceptationCompleted = port.acceptRequestResident(idRequest, neighborhood);
        if (!acceptationCompleted) {
            log.error("Accept id: {} in neighborhood: {} no exists", idRequest, neighborhood);
        }
    }
}
