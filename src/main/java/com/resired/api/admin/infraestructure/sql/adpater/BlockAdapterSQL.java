package com.resired.api.admin.infraestructure.sql.adpater;

import com.resired.api.admin.domain.repository.BlockPort;
import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.admin.infraestructure.sql.jpa.BlockJpaRepository;
import com.resired.api.resident.infraestructure.sql.orm.BlockOrm;
import com.resired.api.resident.infraestructure.sql.orm.HomeOrm;
import com.resired.api.resident.infraestructure.sql.orm.NeighborhoodOrm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BlockAdapterSQL implements BlockPort {

    private BlockJpaRepository blockJpa;

    @Override
    public void createBlock(Integer neighborhoodId, GroupingType type, String name, List<String> homes) {
        List<HomeOrm> listHomes = new ArrayList<>();
        BlockOrm entity = new BlockOrm();
        for (String home : homes) {
            HomeOrm homeOrm = new HomeOrm();
            homeOrm.setNumber(home);
            homeOrm.setBlock(entity);
            homeOrm.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
            listHomes.add(homeOrm);
        }
        entity.setHomes(listHomes);
        entity.setName(name);
        entity.setType(type);
        NeighborhoodOrm neighborhoodOrm = new NeighborhoodOrm();
        neighborhoodOrm.setId(neighborhoodId);
        entity.setNeighborhoodOrm(neighborhoodOrm);
        blockJpa.save(entity);
    }

    @Override
    public List<BlockVo> getAllBlocksByNeighborhoodId(Integer neighborhoodId) {
        List<BlockOrm> listBlocks = blockJpa.findAllByNeighborhoodOrmId(neighborhoodId);
        return listBlocks.stream()
            .map(block -> new BlockVo(block.getType(), block.getName()))
            .toList();
    }
}
