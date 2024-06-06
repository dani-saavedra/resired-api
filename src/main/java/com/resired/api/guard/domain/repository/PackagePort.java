package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Package;

import java.time.LocalDateTime;
import java.util.List;

public interface PackagePort {

    void registerPackage(Package packet);

    List<Package> findAllByNeighborhoodIdAndStartDate(Integer neighborhoodId, LocalDateTime date);

    Package findPackageByIdAndByNeighborhoodId(Integer packageId, Integer neighborhoodId);

    void updatePackage(Package packet);
}
