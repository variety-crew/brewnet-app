package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_letter_of_purchase")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LetterOfPurchase {

    @Id
    @Column(name = "letter_of_purchase_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer letterOfPurchaseCode;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "approved", nullable = false)
    @Enumerated(EnumType.STRING)
    private IsApproved approved;

    @Column(name = "sum_price", nullable = false)
    private Integer sumPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correspondent_code", nullable = false)
    private Correspondent correspondent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = false)
    private PurchaseMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_code", nullable = false)
    private Storage storage;
}
