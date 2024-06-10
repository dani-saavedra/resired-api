package com.resired.api.admin.domain.entity;

import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.admin.domain.vo.NeighborhoodCategory;
import com.resired.api.admin.domain.vo.ResidenceType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Neighborhood {


    private final Integer id;
    private final String name;
    private final String document;
    private final String address;
    private final String city;
    private final Integer socioeconomicLevel;
    @Setter
    private ResidenceType residenceType;
    @Setter
    private GroupingType groupingType;
    @Setter
    private String preferredName;
    @Setter
    private String communityType;

    private final NeighborhoodCategory category;
    @Setter
    private String securityCompany;
    @Setter
    private Integer towers;
    @Setter
    private Integer homes;

    public Neighborhood(Integer id, String name, String document, String address, String city,
                        Integer socioeconomicLevel, ResidenceType residenceType, GroupingType groupingType,
                        String preferredName, String communityType, NeighborhoodCategory category, String securityCompany,
                        Integer towers, Integer homes) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.address = address;
        this.city = city;
        this.socioeconomicLevel = socioeconomicLevel;
        this.residenceType = residenceType;
        this.groupingType = groupingType;
        this.preferredName = preferredName;
        this.communityType = communityType;
        this.category = category;
        this.securityCompany = securityCompany;
        this.towers = towers;
        this.homes = homes;
    }
}
