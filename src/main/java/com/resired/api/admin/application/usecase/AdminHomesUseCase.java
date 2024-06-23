package com.resired.api.admin.application.usecase;

import com.resired.api.admin.application.dto.HomeDTO;
import com.resired.api.admin.application.exception.InvalidUserException;
import com.resired.api.admin.application.port.HomeExcelPort;
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
    private final HomeExcelPort homeExcelPort;

    public List<HomeDTO> getHomesByNeighborhood(Integer neighborhood) {
        List<Home> homesByNeighborhood = homePort.getHomesByNeighborhood(neighborhood);
        return homesByNeighborhood
            .stream()
            .map(home -> new HomeDTO(home.getId(), home.getName(), home.getBlock(), home.getResidents()))
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
        List<Home> homes = homeExcelPort.findAndGetHomes(rawExcel, neighborhood);
        homePort.saveAll(homes);
    }
}
