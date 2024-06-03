package com.resired.api.resident.domain.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Home {

    private final Integer id;
    private String name;
    private Integer neighborhoodId;
    private List<Package> packages;

    public Home(Integer id) {
        this.id = id;
        packages = new ArrayList<>();
    }

    public Home(Integer id, String name, Integer neighborhoodId) {
        this.id = id;
        this.name = name;
        this.neighborhoodId = neighborhoodId;
        packages = new ArrayList<>();
    }

    public void addPackages(Package newPackage) {
        packages.add(newPackage);
    }


}
