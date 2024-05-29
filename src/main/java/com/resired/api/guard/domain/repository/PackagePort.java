package com.resired.api.guard.domain.repository;

import com.resired.api.guard.domain.entity.Package;

public interface PackagePort {

    void registerPackage(Package packet);
}
