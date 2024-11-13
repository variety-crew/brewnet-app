package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeHistoryResponseVO {
    private int exchangeStockHistoryCode;
    private String status;
    private String manager;
    private String createdAt;
    private String confirmed;
    private String exchangeCode;
    private String exchangeManager;
}
