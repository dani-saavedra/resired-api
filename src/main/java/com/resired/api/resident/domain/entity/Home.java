package com.resired.api.resident.domain.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Home {

    private Long id;
    @Getter
    private List<Package> packages;

    public Home(Long id) {
        this.id = id;
        packages = new ArrayList<>();
    }

    public void addPackages(Package newPackage) {
        packages.add(newPackage);
    }
}
