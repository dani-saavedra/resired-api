package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.resident.domain.entity.Package;
import com.resired.api.resident.domain.enums.PackageStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "package")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageOrm {

    @Id
    private Integer id;

    @Column
    private Integer guardId;

    @JoinColumn(name = "home_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private HomeOrm home;

    @Column
    private String receiver;

    @Column
    private String trackingNumber;

    @Column
    private String packageTransporter;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(50)")
    private PackageStatusEnum status;

    @Column(name = "received_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public Package toEntity() {
        return new Package(this.id, this.receiver, this.trackingNumber, this.packageTransporter,
            this.description, this.status, this.createdDate);
    }
}
