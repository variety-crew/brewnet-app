package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_exchange_status_history")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeStatusHistory {
    @Id
    @Column(name = "exchange_status_history_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exchangeStatusHistoryCode;      // 교환 상태 이력 코드

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExchangeStatus status;              // 교환상태

    @Column(name = "created_at", nullable = false)
    private String createdAt;                   // 생성일시

    @Column(name = "active", nullable = false)
    private boolean active;                     // 활성화 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_code", nullable = false)
    private Exchange exchange;                   // 교환코드
}
