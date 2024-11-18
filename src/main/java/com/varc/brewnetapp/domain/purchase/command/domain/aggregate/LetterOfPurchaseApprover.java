package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_letter_of_purchase_approver")
@IdClass(LetterOfPurchaseApproverId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LetterOfPurchaseApprover {

    @Id
    @Column(name = "member_code")
    private Integer memberCode;

    @Id
    @Column(name = "letter_of_purchase_code")
    private Integer letterOfPurchaseCode;

    @Column(name = "approved", nullable = false)
    @Enumerated(EnumType.STRING)
    private IsApproved approved;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "comment")
    private String comment;
}
