package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.entity.Home;

import java.util.List;

public interface HomePort {

    Home getPackages(Integer homeId);

    Home getHomeById(Integer homeId);

    String getHomeNumberById(Integer homeId);

    List<Home> getHomesByNeighborhood(Integer neighborhoodId);

    List<Home> getHomesByBlocks(Integer blockId);

    void updateHome(Integer homeId, String number, Double squareMeter);
}
