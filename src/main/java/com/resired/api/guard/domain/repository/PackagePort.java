package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Package;

import java.util.List;

public interface PackagePort {

    void registerPackage(Package packet);

    List<Package> findAllByNeighborhoodId(Integer neighborhoodId);
}
