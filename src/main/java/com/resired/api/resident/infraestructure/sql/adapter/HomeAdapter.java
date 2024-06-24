package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.infraestructure.sql.jpa.HomeJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
            .map(orm -> new Home(orm.getId(), orm.getNumber(),
                orm.getBlock().getNeighborhoodOrm().getId(),
                orm.getResidents().size()))
            .orElse(null);
    }

    @Override
    public String getHomeNumberById(Integer homeId) {
        HomeOrm homeOrm = jpaRepository.findById(homeId).orElse(null);
        return homeOrm != null ? homeOrm.getNumber() : null;
    }

    @Override
    public List<Home> getHomesByNeighborhood(Integer neighborhoodId) {
        List<HomeOrm> byBlockNeighborhoodOrmId = jpaRepository.findByBlockNeighborhoodOrmId(neighborhoodId);
        return byBlockNeighborhoodOrmId
            .stream()
            .map(orm -> {
                List<UserRolOrm> activeResident = orm.getResidents()
                    .stream()
                    .filter(UserRolOrm::isActive).toList();
                return new Home(orm.getId(), orm.getNumber(), orm.getBlock().getName(),
                    activeResident.size());
            }).toList();
    }

    @Override
    public List<Home> getHomesByBlocks(Integer blockId) {
        return jpaRepository.findByBlockId(blockId)
            .stream()
            .map(orm -> new Home(orm.getId(), orm.getNumber(), orm.getBlock().getName(), orm.getResidents().size())).toList();
    }

    @Override
    public void updateHome(Integer homeId, String number, Double squareMeter) {
        jpaRepository.updateHome(number, null, homeId);
    }
}
