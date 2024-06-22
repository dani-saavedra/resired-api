package com.resired.api.admin.domain.entity;

import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.admin.domain.vo.NeighborhoodCategory;
import com.resired.api.admin.domain.vo.ResidenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Neighborhood {


    private final Integer id;
    private final String name;
    private String document;
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

    public Neighborhood(Integer id, String name, String address, String city,
                        NeighborhoodCategory category, Integer socioeconomicLevel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.category = category;
        this.socioeconomicLevel = socioeconomicLevel;
    }
}
