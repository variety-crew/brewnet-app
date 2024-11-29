package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "tbl_franchise_mandatory_purchase")
@ToString
public class FranchiseMandatoryPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "franchise_mandatory_purchase_code")
    private int franchiseMandatoryPurchaseCode;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "franchise_code", nullable = false)
    private int franchiseCode;

    @Column(name = "mandatory_purchase_code", nullable = false)
    private int mandatoryPurchaseCode;
}
