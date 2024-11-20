package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_order_print")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchasePrint {

    @Id
    @Column(name = "order_print_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderPrintCode;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "printed_at", nullable = false)
    private LocalDateTime printedAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = false)
    private PurchaseMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_of_purchase_code", nullable = false)
    private LetterOfPurchase letterOfPurchase;
}
