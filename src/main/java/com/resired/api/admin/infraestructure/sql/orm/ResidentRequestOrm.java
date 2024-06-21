package com.resired.api.admin.infraestructure.sql.orm;

import com.resired.api.admin.domain.vo.DecisionRequestResident;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "resident_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentRequestOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String document;

    @Column
    private String house;

    @Column
    private Integer neighborhood;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision", columnDefinition = "VARCHAR(50)")
    private DecisionRequestResident decision;

    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;


    public ResidentRequestOrm(String firstName, String lastName, String email, String document, String house,
                              Integer neighborhood) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.document = document;
        this.house = house;
        this.neighborhood = neighborhood;
        this.createdDate = LocalDateTime.now(ZoneOffset.UTC);
        this.decision = DecisionRequestResident.PENDING;
    }
}
