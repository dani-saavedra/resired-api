package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.resident.domain.enums.PackageStatusEnum;
import com.resired.api.security.infraestructure.sql.orm.UserOrm;
import com.resired.api.utils.FormatDate;
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

    @ManyToOne
    @JoinColumn(name = "guard_received_id")
    private UserOrm guardReceived;

    @ManyToOne
    @JoinColumn(name = "home_id")
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

    @ManyToOne
    @JoinColumn(name = "guard_delivered_id")
    private UserOrm deliveredGuard;

    @Column(name = "receiver_last_four_digits")
    private String receiverLastFourDigits;

    public String guardDelivered() {
        if (deliveredGuard != null) {
            return deliveredGuard.fullName();
        }
        return null;
    }

    public String guardReceived() {
        if (guardReceived != null) {
            return guardReceived.fullName();
        }
        return null;
    }
}
