package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeConfirmed;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeHistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeHistoryVO {
    private int exchangeStockHistoryCode;
    private ExchangeHistoryStatus status;
    private String manager;
    private String createdAt;
    private ExchangeConfirmed confirmed;
    private String exchangeCode;
    private String exchangeManager;
}
