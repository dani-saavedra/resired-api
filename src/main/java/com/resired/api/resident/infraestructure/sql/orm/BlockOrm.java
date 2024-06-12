package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.admin.domain.vo.GroupingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "block")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(50)")
    private GroupingType type;

    @JoinColumn(name = "neighborhood_id")
    @ManyToOne
    private NeighborhoodOrm neighborhoodOrm;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<HomeOrm> homes;
}
