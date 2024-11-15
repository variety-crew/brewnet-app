package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExMember;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeDraftApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeReason;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_exchange")
@NoArgsConstructor
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
    private ExchangeApproval approved;  // 교환결재승인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code", nullable = false)
    private ExOrder order;              // 주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = true)
    private ExMember memberCode;        // 교환 기안자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_code", nullable = true)
    private ExMember deliveryCode;      // 배송기사

    @Enumerated(EnumType.STRING)
    @Column(name = "drafter_approved", nullable = true)
    private ExchangeDraftApproval drafter_approved;     // 기안자의 교환 승인 여부

    @Column(name = "sum_price", nullable = false)
    private int sum_price;              // 교환 금액 합계
}
