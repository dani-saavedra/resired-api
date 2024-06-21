package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.application.dto.ResidentRequestDto;
import com.resired.api.admin.application.repository.ResidentRequestPort;
import com.resired.api.admin.infraestructure.sql.jpa.ResidentRequestJpaRepository;
import com.resired.api.admin.infraestructure.sql.orm.ResidentRequestOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ResidentRequestAdapterSQL implements ResidentRequestPort {

    private ResidentRequestJpaRepository requestJpaRepository;


    @Override
    public void registerRequestResident(ResidentRequestDto dto) {
        ResidentRequestOrm orm = new ResidentRequestOrm(dto.firstName(), dto.lastName(), dto.email(),
            dto.document(), dto.house(), dto.neighborhood());
        requestJpaRepository.save(orm);
    }
}
