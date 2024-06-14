package com.resired.api.resident.domain.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Home {

    private final Integer id;
    private String name;
    private String block;
    private Integer neighborhood;
    private List<Package> packages;

    public Home(Integer id) {
        this.id = id;
        packages = new ArrayList<>();
    }

    public Home(Integer id, String name, Integer neighborhood) {
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        packages = new ArrayList<>();
    }

    public Home(Integer id, String name, String block) {
        this.id = id;
        this.name = name;
        this.block = block;
    }

    public void addPackages(Package newPackage) {
        packages.add(newPackage);
    }


}
