package com.resired.api.resident.domain.entity;

import com.resired.api.admin.domain.vo.GroupingType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Home {

    private final Integer id;
    @Setter
    private String name;
    private String block;
    private Integer neighborhood;
    private List<Package> packages;
    @Setter
    private Double squareMeter;
    private GroupingType type;
    private Integer residents;

    public Home(Integer id) {
        this.id = id;
        packages = new ArrayList<>();
    }

    public Home(Integer id, String name, Integer neighborhood, Integer residents) {
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        packages = new ArrayList<>();
        this.residents = residents;
    }

    public Home(Integer id, String name, String block, Integer residents) {
        this.id = id;
        this.name = name;
        this.block = block;
        this.residents = residents;
    }

    public Home(Integer id, String name, String block, GroupingType type) {
        this.id = id;
        this.name = name;
        this.block = block;
        this.type = type;
    }

    public void addPackages(Package newPackage) {
        packages.add(newPackage);
    }

    public String getFullHomeName() {
        if (GroupingType.NINGUNA.equals(type)) {
            return this.name;
        } else {
            return this.block + ", " + this.name;
        }
    }

}
