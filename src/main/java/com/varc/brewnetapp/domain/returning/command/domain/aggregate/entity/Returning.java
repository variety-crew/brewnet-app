package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.Order;
import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Data
@Getter
@Entity
@Table(name = "tbl_return")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Returning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_code", nullable = false)
    private Integer returningCode;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private ReturningStatus reason;

    @Column(name = "explanation", length = 255, nullable = false)
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private Approval approvalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member memberCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_code")
    private Member deliveryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "drafter_approved", nullable = false)
    private DrafterApproved drafterApproved;

    @Column(name = "sum_price", nullable = false)
    private int sumPrice;
}
