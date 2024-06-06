package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsOrm {

    @Id
    private Integer id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String category;

    @Column(name = "neighborhood_id")
    private Integer neighborhoodId;

    @Column
    private String image;

    @Column
    private LocalDateTime createdDate;

}
