package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.admin.domain.vo.NeighborhoodCategory;
import com.resired.api.admin.domain.vo.ResidenceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "NEIGHBORHOOD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String document;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private Integer socioeconomicLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "residence_type", columnDefinition = "VARCHAR(50)")
    private ResidenceType residenceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "grouping_type", columnDefinition = "VARCHAR(50)")
    private GroupingType groupingType;

    @Column
    private String preferredName;

    @Column
    private String communityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "VARCHAR(50)")
    private NeighborhoodCategory category;

    @Column
    private Integer towers;

    @Column
    private Integer homes;

    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDate updateDate;

    public NeighborhoodOrm(String name, String address, String city, Integer socioeconomicLevel, String communityType,
                           NeighborhoodCategory category) {
        this.name = name;
        this.preferredName = name;
        this.address = address;
        this.city = city;
        this.socioeconomicLevel = socioeconomicLevel;
        this.creationDate = LocalDateTime.now(ZoneOffset.UTC);
        this.communityType = communityType;
        this.category = category;
    }
}


