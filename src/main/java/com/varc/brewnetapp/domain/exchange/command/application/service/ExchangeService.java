package com.varc.brewnetapp.domain.exchange.command.application.service;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;

public interface ExchangeService {
    void createExchange(String loginId, ExchangeReqVO exchangeReqVO);

    boolean cancelExchange(String loginId, Integer exchangeCode);
}
