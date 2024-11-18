package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import com.varc.brewnetapp.domain.purchase.common.PositionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_position")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchasePosition {

    @Id
    @Column(name = "position_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionCode;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private PositionName name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
