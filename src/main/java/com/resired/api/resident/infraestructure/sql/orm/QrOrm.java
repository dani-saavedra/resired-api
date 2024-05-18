package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static java.sql.Types.TINYINT;

@Entity
@Table(name = "QR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JdbcTypeCode(TINYINT)
    private boolean available;

    @JoinColumn(name = "visitor_id")
    @ManyToOne
    private VisitorOrm visitor;
}
