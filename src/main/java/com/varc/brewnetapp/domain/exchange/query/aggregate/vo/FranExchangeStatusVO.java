package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FranExchangeStatusVO {
    private ExchangeStatus status;  // 교환상태
    private String processedAt;     // 처리일시
}
