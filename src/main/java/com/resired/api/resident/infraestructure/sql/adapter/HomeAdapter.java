package com.resired.api.resident.infraestructure.sql.adapter;

import com.resired.api.admin.infraestructure.sql.jpa.BlockJpaRepository;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import com.resired.api.resident.infraestructure.sql.jpa.HomeJpaRepository;
import com.resired.api.resident.infraestructure.sql.jpa.NeighborhoodJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.BlockOrm;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HomeAdapter implements HomePort {

    private final HomeJpaRepository jpaRepository;
    private final BlockJpaRepository blockJpaRepository;
    private final NeighborhoodJpaRepository neighborhoodJpaRepository;

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

    @Override
    public void saveAll(List<Home> homes) {
        homes.forEach(home -> {
            BlockOrm blockOrm = getBlockOrm(home);

            if (blockOrm == null) {
                Optional<NeighborhoodOrm> neighborhood = neighborhoodJpaRepository.findById(home.getNeighborhood());
                blockOrm = new BlockOrm();
                blockOrm.setName(home.getBlock());
                blockOrm.setType(home.getType());
                blockOrm.setNeighborhoodOrm(neighborhood.get());
                blockOrm = blockJpaRepository.save(blockOrm);
            }

            HomeOrm homeOrm = new HomeOrm();
            homeOrm.setBlock(blockOrm);
            homeOrm.setNumber(home.getName());
            homeOrm.setSquareMeter(BigDecimal.valueOf(home.getSquareMeter()));
            homeOrm.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));

            jpaRepository.save(homeOrm);
        });
    }

    private BlockOrm getBlockOrm(Home home) {
        Optional<BlockOrm> blockOpt = blockJpaRepository.findByNameAndNeighborhoodOrmId(home.getBlock(), home.getNeighborhood());
        return blockOpt.orElse(null);
    }
}
