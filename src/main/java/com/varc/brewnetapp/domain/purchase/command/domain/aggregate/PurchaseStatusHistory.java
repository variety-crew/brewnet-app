package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import com.varc.brewnetapp.domain.purchase.common.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_purchase_status_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseStatusHistory {

    @Id
    @Column(name = "purchase_status_history_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseStatusHistoryCode;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_of_purchase_code", nullable = false)
    private LetterOfPurchase letterOfPurchase;
}
