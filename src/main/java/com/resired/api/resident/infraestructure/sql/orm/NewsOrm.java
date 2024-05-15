package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.resident.domain.enums.NewsCategoryEnum;
import jakarta.persistence.*;
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
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private NewsCategoryEnum category;

    @Column(name = "neighborhood_id")
    private Long neighborhoodId;

    @Column
    private String image;

    @Column
    private LocalDateTime createdDate;

}
