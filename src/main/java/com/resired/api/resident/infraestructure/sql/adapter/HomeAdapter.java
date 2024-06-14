package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.infraestructure.sql.jpa.HomeJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public String getHomeNumberById(Integer homeId) {
        HomeOrm homeOrm = jpaRepository.findById(homeId).orElse(null);
        return homeOrm != null ? homeOrm.getNumber() : null;
    }

    @Override
    public List<Home> getHomesByNeighborhood(Integer neighborhoodId) {
        return jpaRepository.findByBlockNeighborhoodOrmId(neighborhoodId)
            .stream()
            .map(orm -> new Home(orm.getId(), orm.getNumber(), orm.getBlock().getName())).toList();
    }

    @Override
    public List<Home> getHomesByBlocks(Integer blockId) {
        return jpaRepository.findByBlockId(blockId)
            .stream()
            .map(orm -> new Home(orm.getId(), orm.getNumber(), orm.getBlock().getName())).toList();
    }
}
