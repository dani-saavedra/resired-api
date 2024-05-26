package com.resired.api.resident.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.resident.domain.enums.NewsCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class News {

    private Integer id;
    private String title;
    private String description;
    private String image;
    private NewsCategoryEnum category;
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
}
