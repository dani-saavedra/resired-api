package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.entity.Home;

public interface HomePort {

    Home getPackages(Integer homeId);

    Home getHomeById(Integer homeId);

    Integer getHomeIdByBlockAndNumberAndNeighborhoodId(String block, String homeNumber, Integer neighborhoodId);

    Integer getHomeIdByNumberAndNeighborhoodId(String homeNumber, Integer neighborhoodId);

    String getHomeNumberById(Integer homeId);
}
