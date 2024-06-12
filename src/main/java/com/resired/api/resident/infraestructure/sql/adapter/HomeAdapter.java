package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.infraestructure.sql.jpa.HomeJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class HomeAdapter implements HomePort {

    private final HomeJpaRepository jpaRepository;

    @Override
    public Home getPackages(Integer homeId) {
        Home home = new Home(homeId);
        jpaRepository.getPackages(homeId).forEach(p -> home.addPackages(p.toEntity()));
        return home;
    }

    @Override
    public Home getHomeById(Integer homeId) {
        return jpaRepository.findById(homeId)
            .map(homeOrm -> new Home(homeOrm.getId(), homeOrm.getNumber(), homeOrm.getBlock().getNeighborhoodOrm().getId()))
            .orElse(null);
    }

    @Override
    public Integer getHomeIdByBlockAndNumberAndNeighborhoodId(Integer block, String homeNumber, Integer neighborhoodId) {
        return jpaRepository.findHomeIdByBlockAndNumberAndNeighborhoodId(block, homeNumber, neighborhoodId);
    }

    @Override
    public Integer getHomeIdByNumberAndNeighborhoodId(String homeNumber, Integer neighborhoodId) {
        return jpaRepository.findHomeIdByNumberAndNeighborhoodId(homeNumber, neighborhoodId);
    }

    @Override
    public String getHomeNumberById(Integer homeId) {
        HomeOrm homeOrm = jpaRepository.findById(homeId).orElse(null);
        return homeOrm != null ? homeOrm.getNumber() : null;
    }
}
