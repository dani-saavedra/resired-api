package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.repository.ResidentRequestPort;
import com.resired.api.admin.infraestructure.sql.jpa.ResidentRequestJpaRepository;
import com.resired.api.admin.infraestructure.sql.orm.ResidentRequestOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ResidentRequestAdapterSQL implements ResidentRequestPort {

    private final ResidentRequestJpaRepository requestJpaRepository;

    @Override
    public void registerRequestResident(ResidentRequestDto dto) {
        ResidentRequestOrm orm = new ResidentRequestOrm(dto.firstName(), dto.lastName(), dto.email(),
            dto.document(), dto.house(), dto.neighborhood());
        requestJpaRepository.save(orm);
    }

    public List<ResidentRequestDto> obtainRequestResident(Integer neighborhood) {
        return requestJpaRepository.findResidentRequestOrmByNeighborhood(neighborhood)
            .stream()
            .map(orm -> new ResidentRequestDto(orm.getFirstName(),
                orm.getLastName(), orm.getEmail(), orm.getDocument(), orm.getHouse(),
                orm.getNeighborhood())).toList();
    }
}
