package com.resired.api.resident.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class News {

    private Integer id;
    private String title;
    private String description;
    private String image;
    private String details;
    private String category;
    @JsonProperty("creation_date")
    private String creationDate;
}
