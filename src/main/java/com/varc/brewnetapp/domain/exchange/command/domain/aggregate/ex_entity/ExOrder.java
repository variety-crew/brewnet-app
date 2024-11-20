package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity;

import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "tbl_order")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExOrder {

    @Id
    @Column(name = "order_code", nullable = false)
    private Integer orderCode;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false)
    private Approval approved;

    @Enumerated(EnumType.STRING)
    @Column(name = "drafter_approved")
    private DrafterApproved drafterApproved;

    @Column(name = "sum_price", nullable = false)
    private Integer sumPrice;

    @Column(name = "franchise_code", nullable = false)
    private Integer franchiseCode;

    @Column(name = "member_code")
    private Integer memberCode;

    @Column(name = "delivery_code")
    private Integer deliveryCode;
}