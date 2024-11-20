package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.exchange.ExchangeReason;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
//@Setter
@Entity
@Table(name = "tbl_exchange")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성 방지
@AllArgsConstructor
@ToString
public class Exchange {

    @Id
    @Column(name = "exchange_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exchangeCode;           // 교환코드

    @Column(name = "comment", nullable = true)
    private String comment;             // 첨언

    @Column(name = "created_at", nullable = false)
    private String createdAt;           // 생성일시

    @Column(name = "active", nullable = false)
    private boolean active;             // 활성화

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private ExchangeReason reason;      // 교환사유

    @Column(name = "explanation", nullable = false)
    private String explanation;         // 교환사유설명

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false)
    private Approval approved;          // 교환결재승인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code", nullable = false)
    private ExOrder order;              // 주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = true)
    private Member memberCode;          // 교환 기안자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_code", nullable = true)
    private Member delivery;            // 배송기사

    @Enumerated(EnumType.STRING)
    @Column(name = "drafter_approved", nullable = true)
    private DrafterApproved drafterApproved;     // 기안자의 교환 승인 여부

    @Column(name = "sum_price", nullable = false)
    private int sumPrice;              // 교환 금액 합계

    @Builder(toBuilder = true)
    public Exchange(String comment, String createdAt, boolean active, ExchangeReason reason, Approval approved,
                    String explanation, ExOrder order, Member memberCode, Member delivery, DrafterApproved drafterApproved, int sumPrice) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.active = active;
        this.reason = reason;
        this.explanation = explanation;
        this.approved = approved;
        this.order = order;
        this.memberCode = memberCode;
        this.delivery = delivery;
        this.drafterApproved = drafterApproved;
        this.sumPrice = sumPrice;
    }
}
