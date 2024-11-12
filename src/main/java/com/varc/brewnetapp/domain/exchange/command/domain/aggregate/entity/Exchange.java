package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
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

    @Column(name = "comment", nullable = false)
    private String comment;             // 비고사항

    @Column(name = "created_at", nullable = false)
    private String createdAt;           // 생성일시

    @Column(name = "active", nullable = false)
    private boolean active;             // 활성화 여부

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reason", nullable = false)
    private ExchangeReason reason;      // 교환사유

    @Column(name = "explanation", nullable = false)
    private String explanation;         // 교환사유설명

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "approved", nullable = false)
    private ExchangeApproval approved;  // 교환결재승인

    @Column(name = "order_code", nullable = false)
    private int orderCode;              // 주문코드

    @Column(name = "approval_line_code", nullable = false)
    private int approvalLineCode;       // 결재라인코드

    @Column(name = "member_code", nullable = false)
    private int memberCode;             // 교환담당자

    @Column(name = "delivery_code", nullable = false)
    private int deliveryCode;           // 배송기사
}
