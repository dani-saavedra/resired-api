package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.HomeDTO;
import com.resired.api.admin.application.exception.BusinessException;
import com.resired.api.admin.application.exception.InvalidUserException;
import com.resired.api.admin.application.service.HomeExcelService;
import com.resired.api.admin.domain.repository.BlockPort;
import com.resired.api.admin.domain.vo.BlockVo;
import com.resired.api.guard.application.dto.HomeNeighborhoodDto;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.resident.domain.repository.HomePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class AdminHomesUseCase {

    private final HomePort homePort;
    private final HomeExcelService homeExcelService;
    private final BlockPort blockPort;

    public List<HomeDTO> getHomesByNeighborhood(Integer neighborhood) {
        List<Home> homesByNeighborhood = homePort.getHomesByNeighborhood(neighborhood);
        return homesByNeighborhood
            .stream()
            .map(home -> new HomeDTO(home.getId(), home.getName(), home.getBlock(), home.getResidents()))
            .toList();
    }

    public List<HomeNeighborhoodDto> getNamesHomesByNeighborhood(Integer neighborhood) {
        List<Home> homesByNeighborhood = homePort.getHomesByNeighborhood(neighborhood);
        return homesByNeighborhood
            .stream()
            .map(home -> new HomeNeighborhoodDto(home.getId(), home.getFullHomeName()))
            .toList();
    }

    public List<HomeDTO> getHomesByBlock(Integer block) {
        return homePort.getHomesByBlocks(block)
            .stream()
            .map(home -> new HomeDTO(home.getId(), home.getName(), home.getBlock(), home.getResidents()))
            .toList();
    }

    public void updateHome(Integer neighbor, Integer homeId, String newNameHome, Double squareMeter) {
        Home home = homePort.getHomeById(homeId);
        if (!Objects.equals(home.getNeighborhood(), neighbor)) {
            throw new InvalidUserException("USER06");
        }
        home.setName(newNameHome);
        home.setSquareMeter(squareMeter);
        homePort.updateHome(home.getId(), newNameHome, squareMeter);
    }

    public void loadHomesFromExcel(InputStream rawExcel, Integer neighborhood) throws IOException {
        List<Home> homes = homeExcelService.findAndGetHomes(rawExcel, neighborhood);
        homePort.saveAll(homes);
    }

    public void addHome(Integer neighbor, String homeName, Integer block, Double squareMeter) {
        BlockVo blockVo = blockPort.getBlockByIdAndNeighbor(block, neighbor);
        if (blockVo == null) {
            throw new BusinessException("Invalid Block", "BLOCK01");
        }
        homePort.save(homeName, block, squareMeter);
    }
}
