package com.varc.brewnetapp.domain.item.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "tbl_mandatory_purchase")
@ToString
public class MandatoryPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mandatory_purchase_code")
    private int mandatoryPurchaseCode;

    @Column(name = "min_quantity", nullable = false)
    private String minQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "item_code", nullable = false)
    private int itemCode;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "satisfied", nullable = false)
    private boolean satisfied;

    @Column(name = "member_code", nullable = false)
    private int memberCode;
}
