package com.resired.api.resident.domain.repository;

import com.resired.api.resident.domain.entity.Home;

public interface HomePort {

    Home getPackages(Integer homeId);

    Home getHomeById(Integer homeId);

    Integer getHomeIdByBlockAndNumber(String block, String homeNumber);

    Integer getHomeIdByNumber(String homeNumber);
}
