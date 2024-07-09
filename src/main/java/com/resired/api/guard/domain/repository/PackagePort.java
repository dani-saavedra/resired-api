package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Package;

import java.util.List;

public interface PackagePort {

    void registerPackage(Package packet);

    List<Package> findAllByNeighborhoodId(Integer neighborhoodId);

    List<Package> findPackagesByStatus(Integer neighborhoodId, String statusEnum);

    List<Package> findByHome(Integer homeId);

    Package findPackageByIdAndByNeighborhoodId(Integer packageId, Integer neighborhoodId);

    void updatePackage(Package packet);
}
