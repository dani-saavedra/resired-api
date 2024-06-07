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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer guardReceivedId;

    @Column(name = "home_id")
    private Integer home;

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

    @Column(name = "guard_delivered_id")
    private Integer deliveredGuardId;

    @Column(name = "receiver_last_four_digits")
    private String receiverLastFourDigits;

    public Package toEntity() {
        return new Package(this.id, this.receiver, this.trackingNumber, this.packageTransporter,
            this.description, this.status, this.createdDate,this.updateDate);
    }
}
