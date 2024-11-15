package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "tbl_exchange_approver")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeApprover {

    @EmbeddedId
    private ExchangeApproverId exchangeApproverId;  // (복합키) 회원코드, 교환코드

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false)
    private ExchangeApproval approved;              // 승인여부

    @Column(name = "created_at", nullable = true)
    private String createdAt;                       // 결재일시

    @Column(name = "comment", nullable = true)
    private String comment;                         // 비고사항

    @Column(name = "active", nullable = false)
    private boolean active;                         // 활성화
}

