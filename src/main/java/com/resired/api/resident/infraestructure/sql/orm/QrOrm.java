package com.resired.api.resident.infraestructure.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    @Column
    private String qr;

    @JoinColumn(name = "visitor_id")
    @ManyToOne
    private VisitorOrm visitor;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime disabledAt;

    public boolean isAvailableToEnter() {
        return !this.getVisitor().isDeleted() && (
            this.getVisitor().isFavorite() || (this.isAvailable() &&
                this.getCreatedAt().plusDays(1).isAfter(LocalDateTime.now(ZoneOffset.UTC))));
    }
}
