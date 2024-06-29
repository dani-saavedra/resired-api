package com.resired.api.resident.infraestructure.sql.orm;

import com.resired.api.admin.domain.vo.GroupingType;
import com.resired.api.resident.domain.entity.Home;
import com.resired.api.security.infraestructure.sql.orm.UserRolOrm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "HOME")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "block")
    @ManyToOne
    private BlockOrm block;

    @Column(name = "home_number")
    private String number;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "square_meter", precision = 10, scale = 1)
    private BigDecimal squareMeter;

    @OneToMany(mappedBy = "home")
    private List<UserRolOrm> residents;

    public Home toBasicInfoHome() {
        return new Home(
            this.id,
            this.number,
            this.block.getName(),
            this.block.getType()
        );
    }

    @Override
    public String toString() {
        return "HomeOrm{" +
            "block=" + block +
            ", id=" + id +
            ", squareMeter=" + squareMeter +
            '}';
    }

    public String getFullHomeName() {
        if (GroupingType.NINGUNA.equals(this.block.getType())) {
            return this.number;
        } else {
            return this.block.getName() + " - " + this.number;
        }
    }
}
