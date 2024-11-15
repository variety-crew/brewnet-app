package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

// 교환 별 결재자들 복합키
@Data
@Embeddable
class ExchangeApproverId implements Serializable {

    @Column(name="member_code")
    private int memberCode;

    @Column(name="exchange_code")
    private int exchangeCode;
}