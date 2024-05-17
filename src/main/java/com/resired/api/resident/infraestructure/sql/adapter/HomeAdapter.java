package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.infraestructure.sql.jpa.HomeJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class HomeAdapter implements HomePort {

    private HomeJpaRepository jpaRepository;

    @Override
    public Home getPackages(Integer homeId) {
        Home home = new Home(homeId);
        jpaRepository.getPackages(homeId).forEach(p -> home.addPackages(p.toEntity()));
        return home;
    }
}
